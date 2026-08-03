import scala.io.Source

object ekta_SortDataset {

  def main(args: Array[String]): Unit = {

    // Read the CSV file
    val filename = "train.csv"
    val lines = Source.fromFile(filename).getLines().toList

    // Header
    val header = lines.head.split(",")

    // Read data (skip header)
    val data = lines.tail.map(_.split(",", -1))

    // Sort by Fare (column index = 8) in descending order
    val sortedData = data.sortBy(row => -row(8).toDouble)

    // Extract top 5 rows
    val top5 = sortedData.take(5)

    // Print header
    println("Top 5 Passengers by Fare")
    println("-------------------------------------------------------------")
    println("PassengerId\tName\t\t\tPclass\tFare")

    // Print top 5 rows
    top5.foreach { row =>
      println(s"${row(0)}\t\t${row(2)}\t\t${row(1)}\t${row(8)}")
    }
  }
}

