package x


//import org.apache.spark._
//import org.apache.spark.streaming._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
//import spark.implicits._

/*
$  spark-submit  --master local[2]   --driver-class-path logging/  --class x.StructuredStreaming  target/scala-2.11/structured-streaming_2.11-1.0.jar
 */


object StructuredStreaming {
  def main(args: Array[String]) {

    val spark = SparkSession.builder.appName("Structured Streaming").
                getOrCreate()

    val clickstream = spark.readStream.format("socket").
                      option("host", "localhost").
                      option("port", 9999)
                      .load()

    clickstream.printSchema

    val query = clickstream.writeStream.
                outputMode("append")
                .format("console")
                .start()

    query.awaitTermination()
    
  }
}

