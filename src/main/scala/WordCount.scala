import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "C:\\hadoop\\")
    val sc = new SparkContext(new SparkConf().setAppName("WordCount").setMaster("local[2]").set("spark.executor.memory","1g"))
    // split each document into words
    val file= sc.textFile("input.txt")
    val counts = file.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
    FileUtil.rmrf("output")   // delete the previous output folder
    counts.saveAsTextFile("output")
    System.out.println(counts)
    Console.in.read()   // wait for key - to provide time to check web console
    sc.stop()
  }
}