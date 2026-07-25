import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._

import java.io.File

object ekta_13_Practical {
  def main(args: Array[String]): Unit = {
    // 1. Automatically locate the dataset
    val csvFile = List(
      "penguins.csv",
      "Penguins.csv",
      "penguins.csv.csv",
      "src/main/scala/penguins.csv"
    ).map(new File(_)).find(_.exists()).getOrElse(new File("penguins.csv"))

    val reader = CSVReader.open(csvFile)
    val data = reader.allWithHeaders()
    reader.close()

    // 2. Filter out missing/NA values
    val validData = data.filter { row =>
      row.getOrElse("culmen_length_mm", "NA") != "NA" &&
        row.getOrElse("culmen_depth_mm", "NA") != "NA" &&
        row.getOrElse("sex", "NA") != "NA"
    }

    // 3. Group dataset by 'sex' (MALE vs FEMALE)
    val maleData = validData.filter(_("sex") == "MALE")
    val femaleData = validData.filter(_("sex") == "FEMALE")

    // Helper method to convert columns to Breeze DenseVectors
    def extractXY(rows: List[Map[String, String]]): (DenseVector[Double], DenseVector[Double]) = {
      val x = DenseVector(rows.map(_("culmen_length_mm").toDouble).toArray)
      val y = DenseVector(rows.map(_("culmen_depth_mm").toDouble).toArray)
      (x, y)
    }

    val (xMale, yMale) = extractXY(maleData)
    val (xFemale, yFemale) = extractXY(femaleData)

    // 4. Create the Breeze-viz Figure & Subplot
    val fig = Figure("Penguin Culmen Analysis")
    val plt = fig.subplot(0)

    // Customize Title and Axes Labels
    plt.title = "Culmen Length vs Culmen Depth"
    plt.xlabel = "Culmen Length (mm)"
    plt.ylabel = "Culmen Depth (mm)"

    // 5. Plot Male points (blue) and Female points (red)
    plt += plot(xMale, yMale, '.', name = "Male", colorcode = "blue")
    plt += plot(xFemale, yFemale, '.', name = "Female", colorcode = "red")

    // Refresh and show the GUI window
    fig.refresh()
  }
}