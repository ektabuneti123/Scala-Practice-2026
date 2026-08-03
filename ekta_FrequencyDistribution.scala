import scala.io.Source

object ekta_FrequencyDistribution {

  def main(args: Array[String]): Unit = {

    // Read CSV file
    val filename = "train.csv"
    val lines = Source.fromFile(filename).getLines().toList

    // Skip header
    val data = lines.tail

    // Extract Pclass column (2nd column, index = 1)
    val pclass = data.map { line =>
      val cols = line.split(",")
      cols(1).trim.toInt
    }

    // Calculate frequency distribution
    val frequency = pclass.groupBy(identity).mapValues(_.size)

    // Sort by Pclass
    val sortedFreq = frequency.toSeq.sortBy(_._1)

    // Print Frequency Distribution
    println("\nFrequency Distribution")
    println("-------------------------------")
    println("Pclass\tFrequency")

    sortedFreq.foreach {
      case (value, freq) =>
        println(s"$value\t$freq")
    }

    // Print Cumulative Frequency
    println("\nCumulative Frequency")
    println("-------------------------------")
    println("Pclass\tFrequency\tCumulative")

    var cumulative = 0

    sortedFreq.foreach {
      case (value, freq) =>
        cumulative += freq
        println(s"$value\t$freq\t\t$cumulative")
    }
  }
}
