package com.testing.biadko

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.types._

object App {


    val spark = SparkSession
      .builder()
      .appName("pvAgregator")
      .master("local[*]")
      .getOrCreate()


  def main(args: Array[String]) = {

// Plik jest bez naglowkow, wiec podajemy typy i nazwy kolumn
    val input_schema = new StructType()
      .add(StructField("url", StringType, true))
      .add(StructField("lang", StringType, true))
      .add(StructField("str_date", StringType, true))
      .add(StructField("user_id", StringType, true))
      .add(StructField("params", StringType, true))

    //wciągmy plik do Dataframe
    val pvDF = spark.read.format("csv")
      .option("sep", "|")
      .schema(input_schema)
      .option("header", "false")
      .load("data/data_input.log")

    //Tworzymy widok SQL. To podejście umożliwi zastosowanie gotowych funkcji SQL do parsowania
    pvDF.createOrReplaceTempView("pv")

    //Wyciągamy id artykulu i id wiki
    val pvDF_parsed_article_wiki_dt = spark
      .sql("" +
        "       SELECT url," +
        "           lang, " +
        "           str_date," +
        "           regexp_replace(str_date,'<\\\\d+>','') as dt," +
        "           user_id," +
        "           params, " +
        "           parse_url(concat(url,params),'QUERY','a') as article_id," +
        "           parse_url(concat(url,params),'QUERY','c') as wiki_id" +
        "     FROM pv ")

    pvDF_parsed_article_wiki_dt.show()

    //wyciągamy id artykulu i eventu odpowiednio dla pierwzego i ostatniego zarejestrowanego zdarzenia
    pvDF_parsed_article_wiki_dt.createOrReplaceTempView("pv_parsed")

    val pvDF_result = spark
      .sql("" +
        "    WITH pv_first_last as (" +
        "   SELECT " +
        "           user_id," +
        "           first_value(article_id) over (partition by user_id order by dt) as first_event_article_id," +
        "           first_value(article_id) over (partition by user_id order by dt DESC) as last_event_article_id," +
        "           first_value(wiki_id) over (partition by user_id order by dt) as first_event_wiki_id," +
        "           first_value(wiki_id) over (partition by user_id order by dt DESC) as last_event_wiki_id," +
        "           count(1) over (partition by user_id) as tmp_cnt_per_user_id" +
        "     FROM pv_parsed )" +
        "   SELECT distinct user_id," +
        "           first_event_article_id," +
        "           last_event_article_id," +
        "           first_event_wiki_id," +
        "           last_event_wiki_id," +
        "           if(concat(first_event_article_id,'-',first_event_wiki_id) = concat(last_event_article_id,'-',last_event_wiki_id),true,false) as is_same_article ," +
        "           if(first_event_wiki_id = last_event_wiki_id, true, false) as is_same_wiki" +
        "   FROM pv_first_last" +
        "   WHERE  tmp_cnt_per_user_id > 1" +
        "   ORDER BY user_id ")
    
    
      //zapisujemy wynik do CSV
      /**pvDF_result.write
        .mode(SaveMode.Overwrite)
        .option("header",true)
        .option("delimiter",",")
        .csv("data/result.csv")
      **/

    pvDF_result.coalesce(1).write
      .mode(SaveMode.Overwrite)
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("delimiter",",")
      .save("data/result_tmp")



  }
}
