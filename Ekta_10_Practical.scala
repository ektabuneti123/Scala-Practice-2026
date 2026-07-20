import com.github.tototoshi.csv._
import java.io.File

object Ekta_10_Practical {

  def main(args: Array[String]): Unit = {

    val reader = CSVReader.open(new File("C:\\Users\\EKTA BUNETI\\OneDrive\\FYCS\\All Practical clg submission\\scala for ds\\student_data.csv"))

    val data = reader.allWithHeaders()

    reader.close()

    val threshold = 15

    val filteredRows = data.filter { row =>
      row.get("G3").exists(value => value.toIntOption.exists(_ > threshold))
    }

    println(s"\nTotal Rows with G3 > $threshold: ${filteredRows.length}\n")

    filteredRows.foreach { row =>
      println(row.values.mkString(", "))
    }
  }
}