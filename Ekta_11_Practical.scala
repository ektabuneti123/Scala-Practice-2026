import com.github.tototoshi.csv._
import java.io.File

object Ekta_12_Practical {

  def main(args: Array[String]): Unit = {

    val reader = CSVReader.open(new File("Mall_Customers.csv"))

    val data = reader.allWithHeaders()

    reader.close()

    println("One-Hot Encoding for Genre\n")

    println("CustomerID\tGenre\tMale\tFemale")

    data.foreach { row =>

      val customerID = row("CustomerID")
      val genre = row("Genre")

      val male = if (genre == "Male") 1 else 0
      val female = if (genre == "Female") 1 else 0

      println(s"$customerID\t\t$genre\t$male\t$female")
    }
  }
}