#!/usr/bin/env bash

# uruchomienie sparka (testowany na Spark 3.1.2)
$SPARK_HOME/bin/spark-submit --name "FnDm" --master local[4]  --conf "spark.executor.extraJavaOptions=-XX:+PrintGCDetails -XX:+PrintGCTimeStamps" ./target/scala-2.12/fndm_2.12-0.1.jar


# Obsluga pliku z wynikami koncowymi. Zmana nazwy pliku i czyszczenie śmieci
echo "Usunięcie starych wyników"
rm -f data/result.csv
echo "przerzucenie pliku z postaci partycjowanego do folderu wynikowego"
cat data/result_tmp/part-*-c000.csv > data/result.csv
rm -rf data/result_tmp