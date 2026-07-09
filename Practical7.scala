import breeze.linalg._

object Practical7 {

  def main(args: Array[String]): Unit = {

    // Create two matrices of the same size
    val mat1 = DenseMatrix(
      (2.0, 4.0),
      (6.0, 8.0)
    )

    val mat2 = DenseMatrix(
      (1.0, 2.0),
      (3.0, 4.0)
    )

    // Element-wise operations
    val addition = mat1 + mat2
    val subtraction = mat1 - mat2
    val multiplication = mat1 * mat2
    val division = mat1 / mat2

    // Display matrices
    println("Matrix 1:")
    println(mat1)

    println("\nMatrix 2:")
    println(mat2)

    println("\nAddition:")
    println(addition)

    println("\nSubtraction:")
    println(subtraction)

    println("\nElement-wise Multiplication:")
    println(multiplication)

    println("\nElement-wise Division:")
    println(division)
  }
}
