object Practical3 {
  def main(args: Array[String]): Unit = {

    val data = List(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)

    val mean = data.sum.toDouble / data.length

    val variance = data.map(x => math.pow(x - mean, 2)).sum / data.length

    val stdDev = math.sqrt(variance)

    println("Data = " + data)
    println("Variance = " + variance)
    println("Standard Deviation = " + stdDev)
  }
}
