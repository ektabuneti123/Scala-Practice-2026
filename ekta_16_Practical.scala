import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object CombinedPlot {
  def main(args: Array[String]): Unit = {
    // 1. Locate msft.csv dataset automatically
    val csvFile = List(
      "msft.csv",
      "MSFT.csv",
      "src/main/scala/msft.csv"
    ).map(new File(_)).find(_.exists()).getOrElse(new File("msft.csv"))

    // 2. Read CSV content
    val reader = CSVReader.open(csvFile)
    val data = reader.allWithHeaders()
    reader.close()

    // 3. Date formatter matched to this Kaggle dataset (e.g., "19-Sep-03")
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ENGLISH)

    val parsedData = data.flatMap { row =>
      try {
        val date = LocalDate.parse(row("Date"), dateFormatter)
        val close = row("Close").toDouble
        Some((date, close))
      } catch {
        case _: Throwable => None // Skip invalid or missing entries
      }
    }.sortBy(_._1)

    // 4. Convert parsed data into Breeze vectors
    val x = DenseVector((0 until parsedData.length).map(_.toDouble).toArray)
    val y = DenseVector(parsedData.map(_._2).toArray)

    // 5. Create figure and single subplot
    val fig = Figure("MSFT - Line + Scatter Plot")
    val plt = fig.subplot(0)

    // 6. Combine Line Plot (trend line) and Scatter Plot (data points)
    plt += plot(x, y, name = "Close Price Line", colorcode = "blue")
    plt += plot(x, y, '.', name = "Close Price Points", colorcode = "red")

    // 7. Labels and Formatting
    plt.xlabel = "Time (Days)"
    plt.ylabel = "Close Price"
    plt.title = "MSFT Close Price - Line + Scatter"

    fig.refresh()
  }
}
