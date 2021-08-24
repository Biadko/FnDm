package com.testing.biadko

//import org.apache.avro.generic.GenericData.StringType
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
//import spark.implicits._

import org.apache.spark.{SparkConf, SparkContext, sql}

object App {

  case class Pv(url:String, langCode:String, pvDate: String, id:String, track:String)

    val spark = SparkSession
      .builder()
      .appName("pvApp")
      .master("local[*]")
      .getOrCreate()


  def main(args: Array[String]) = {
    /**
    spark.sql("select 'gg' as a,'hh' as b").show()

    val RDD = spark.sparkContext
      .textFile("data/data_input2.log")
      .map(_.split("|"))
      .map(attributes => Pv(attributes(0), attributes(1), attributes(2), attributes(3), attributes(4)))
      .take(5)

    val DF = spark.createDataFrame(RDD)
      DF.show()


    //val sc = spark.sparkContext

    val rdd = spark.sparkContext.parallelize(
      Seq(
        ("first", Array(1.0, 4.0, 4.0)),
        ("test", Array(1.0, 4.0, 4.0)),
        ("choose", Array(1.0, 4.0, 4.0))
      )
    )

    val dfWithoutSchema = spark.createDataFrame(rdd)
    dfWithoutSchema.show()
**/
    //val dfPvLog = spark.read.textFile("data/data_input2.log")
    //dfPvLog.show(5)

    val schema = new StructType()
      .add(StructField("url", StringType, true))
      .add(StructField("lang", StringType, true))
      .add(StructField("str_date", StringType, true))
      .add(StructField("UID", StringType, true))
      .add(StructField("params", StringType, true))


    val fdPv = spark.read.format("csv")
      .option("sep", "|")
      //.option("inferSchema", "true")
      .schema(schema)
      .option("header", "false")
      .load("data/data_input2.log")

    fdPv.show()

  }
}
