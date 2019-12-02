package x


import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.storage.StorageLevel

/*
$   ~/spark/bin/spark-submit  --master local[2]   --driver-class-path logging/  --class x.OverTCP target/scala-2.11/over-tcp_2.11-1.0.jar
 */


object OverTCP {
  def main(args: Array[String]) {

    val sparkConf = new SparkConf().setAppName("BlkIPOverTCP")

    // TODO  1 : define window duration for 10 seconds
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    // listen on port 9999, only cache it local memory
    val lines = ssc.socketTextStream("localhost", 9999, StorageLevel.MEMORY_ONLY)
    lines.print

    // TODO-2 : filter lines that contains 'blocked'
    /*
    val blocked = lines.filter(line => line.contains("???"))
    val blocked2 = blocked.map("##BLOCKED:" + _) // better print
    blocked2.print
    */

    // TODO-3  : Save both RDDs (and uncomment this block)
    /*
    blocked.saveAsTextFiles("out-blocked")
    */

    // BONUS LAB : On those blocked lines, extract just the IP address
    // hint  : data  format:
    // timestamp, ip, userid, action, domain, campaign, cost, sessionid
    // 1420070400000,ip_1,user_5,clicked,facebook.com,campaign_6,139,session_98
    // hint : separator is comma
    // hint : ip address is second element : index (1)
    /*
    // extract lines with more than one column
    val blocked3 = blocked.filter(_.split(",").size > 1)
    val blockedIPs = blocked3.map(line => line.split("???")(???))
    val blockedIPs2 = blockedIPs.map("##BLOCKED-IP:" + _) // better print
    blockedIPs2.print()
    */


    // kick off the processing
    ssc.start()
    ssc.awaitTermination()
  }
}
