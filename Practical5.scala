import breeze.linalg._
import breeze.stats.distributions._

object Practical5 {
  def main(args: Array[String]): Unit = {

    // Random matrix (3x3)
    val matrix = DenseMatrix.rand[Double](3, 3)

    // Transpose
    val transpose = matrix.t

    // Determinant
    val determinant = det(matrix)

    println("Matrix:")
    println(matrix)

    println("Transpose:")
    println(transpose)

    println("Determinant:")
    println(determinant)
  }
}
