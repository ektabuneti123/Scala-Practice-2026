import breeze.linalg._

object Practical6 {

  def main(args: Array[String]): Unit = {

    // Create a 4x4 matrix
    val matrix = DenseMatrix(
      (2, 4, 6, 8),
      (1, 3, 5, 7),
      (9, 11, 13, 15),
      (10, 12, 14, 16)
    )

    // Display the original matrix
    println("Original Matrix:")
    println(matrix)

    // Extract sub-matrix (rows 1 to 2, columns 1 to 3)
    val subMatrix = matrix(1 to 2, 1 to 3)

    // Display the sub-matrix
    println("\nSub-Matrix:")
    println(subMatrix)

    // Calculate row sums
    val rowSums = sum(subMatrix(*, ::))

    println("\nRow Sums:")
    println(rowSums)

    // Calculate column sums
    val colSums = sum(subMatrix(::, *))

    println("\nColumn Sums:")
    println(colSums.t)
  }
}