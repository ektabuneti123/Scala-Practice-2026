import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object ekta_15_Practical {
  def main(args: Array[String]): Unit = {
    // 1. Locate AAPL.csv automatically
    val csvFile = List(
      "AAPL.csv",
      "aapl.csv",
      "src/main/scala/AAPL.csv"
    ).map(new File(_)).find(_.exists()).getOrElse(new File("AAPL.csv"))

    // 2. Read CSV content
    val reader = CSVReader.open(csvFile)
    val data = reader.allWithHeaders()
    reader.close()

    // 3. Parse date and closing price
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val parsedData = data.flatMap { row =>
      try {
        val date = LocalDate.parse(row("Date"), dateFormatter)
        val close = row("Close").toDouble
        Some((date, close))
      } catch {
        case _: Throwable => None // skip empty or invalid rows
      }
    }.sortBy(_._1)

    // 4. Prepare vectors for plotting (X = index/time, Y = close price)
    val x = DenseVector((0 until parsedData.length).map(_.toDouble).toArray)
    val y = DenseVector(parsedData.map(_._2).toArray)

    // 5. Generate Line Plot
    val fig = Figure("Apple Stock Price Trend")
    val plt = fig.subplot(0)

    plt += plot(x, y, name = "Close Price", colorcode = "blue")
    plt.title = "AAPL Closing Price Over Time"
    plt.xlabel = "Time (Days)"
    plt.ylabel = "Close Price ($)"

    fig.refresh()
  }
}
