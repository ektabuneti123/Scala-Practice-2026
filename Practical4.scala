import breeze.linalg._

object Practical4 {
  def main(args: Array[String]): Unit = {

    // Sparse vector
    val sparseVec = SparseVector(0.0, 1.0, 0.0, 2.0)

    // Convert sparse → dense (ADDED STEP)
    val denseVec = DenseVector(sparseVec.toArray)

    // Another vector
    val anotherVec = DenseVector(1.0, 2.0, 3.0, 4.0)

    val sum = breeze.linalg.sum(denseVec)
    val mean = sum / denseVec.length
    val dot = denseVec dot anotherVec

    println("Dense Vector: " + denseVec)
    println("Sum: " + sum)
    println("Mean: " + mean)
    println("Dot Product: " + dot)
  }
}
