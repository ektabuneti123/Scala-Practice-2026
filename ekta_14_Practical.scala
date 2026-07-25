import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._
import java.io.File

object ekta_14_Practical {
  def main(args: Array[String]): Unit = {
    // 1. Locate winequality.csv automatically
    val csvFile = List(
      "winequality.csv",
      "Winequality.csv",
      "winequality-red.csv",
      "src/main/scala/winequality.csv"
    ).map(new File(_)).find(_.exists()).getOrElse(new File("winequality.csv"))

    // 2. Read CSV data
    val reader = CSVReader.open(csvFile)
    val data = reader.allWithHeaders()
    reader.close()

    // 3. Extract 'alcohol' values
    val alcoholValues = DenseVector(
      data.flatMap(row => row.get("alcohol").orElse(row.get("fixed acidity")))
        .filter(v => v != "NA" && v.nonEmpty)
        .map(_.toDouble)
        .toArray
    )

    // 4. Create Breeze-viz Figure
    val fig = Figure("Wine Dataset Histogram Analysis")

    // 5. Plot histograms with different bin sizes (5, 12, 25)
    val binSizes = List(5, 12, 25)

    for ((bins, idx) <- binSizes.zipWithIndex) {
      val plt = fig.subplot(1, binSizes.length, idx)
      plt += hist(alcoholValues, bins)
      plt.title = s"Histogram with $bins bins"
      plt.xlabel = "Alcohol Content"
      plt.ylabel = "Frequency"
    }

    fig.refresh()
  }
}