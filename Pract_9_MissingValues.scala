package practical9

import com.github.tototoshi.csv._
import java.io.File
import scala.util.Try

object Pract_9_MissingValues {

  def main(args: Array[String]): Unit = {

    // Read CSV file
    val inputFile = new File("Life Expectancy Data.csv")
    val reader = CSVReader.open(inputFile)
    val allRows = reader.allWithHeaders()
    reader.close()

    // Numeric columns (change if needed according to your dataset)
    val numericColumns = Seq("Life expectancy ", "Adult Mortality", "BMI ", "GDP")

    // Step 1: Calculate mean and count missing values
    val stats: Map[String, (Double, Int)] = numericColumns.map { col =>

      val values = allRows.map(row => row.getOrElse(col, "").trim)

      val validNumbers = values.flatMap(v => Try(v.toDouble).toOption)

      val missingCount = values.count(v => Try(v.toDouble).isFailure)

      val mean =
        if (validNumbers.nonEmpty) validNumbers.sum / validNumbers.size
        else 0.0

      (col, (mean, missingCount))

    }.toMap

    // Step 2: Display report
    println("\n------ Missing Data Report ------")

    stats.foreach {
      case (col, (mean, missingCount)) =>
        println(f"Column: $col")
        println(s"Missing Values : $missingCount")
        println(f"Mean Value     : $mean%.2f")
        println()
    }

    // Step 3: Replace missing values with mean
    val cleanedRows = allRows.map { row =>

      numericColumns.foldLeft(row) { (accRow, col) =>

        val value = accRow.getOrElse(col, "").trim

        val newValue = Try(value.toDouble).toOption match {
          case Some(_) => value
          case None    => f"${stats(col)._1}%.2f"
        }

        accRow.updated(col, newValue)
      }
    }

    // Step 4: Save cleaned CSV
    val outputFile = new File("LifeExpectancy_Cleaned.csv")

    val writer = CSVWriter.open(outputFile)

    val headers = cleanedRows.head.keys.toSeq

    writer.writeRow(headers)

    cleanedRows.foreach(row => writer.writeRow(headers.map(row)))

    writer.close()

    println("\nMissing values replaced successfully.")
    println("New file created: LifeExpectancy_Cleaned.csv")
  }
}
