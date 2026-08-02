import scala.io.Source

object Practical2_MovingAverage {

  def main(args: Array[String]): Unit = {

    val stream = getClass.getResourceAsStream("/SBI Dataset.csv")

    if (stream == null) {
      println("Error: Dataset not found!")
      return
    }

    val file = Source.fromInputStream(stream)

    // Feature Engineering: Read Close Price
    val closePrices = file.getLines().drop(1).flatMap { line =>
      val cols = line.split(",")
      if (cols.length > 4)
        cols(4).trim.toDoubleOption
      else
        None
    }.toList

    file.close()

    val window = 5

    // Simple Moving Average (SMA)
    val sma = closePrices.sliding(window).map(_.sum / window).toList

    // Weighted Moving Average (WMA)
    val weights = (1 to window).toList
    val weightSum = weights.sum.toDouble

    val wma = closePrices.sliding(window).map { values =>
      values.zip(weights).map { case (v, w) => v * w }.sum / weightSum
    }.toList

    // Exponential Moving Average (EMA)
    val alpha = 0.3

    var ema = List(closePrices.head)

    for (i <- 1 until closePrices.length) {
      val value = alpha * closePrices(i) + (1 - alpha) * ema.last
      ema = ema :+ value
    }

    println("Dataset Size : " + closePrices.length)

    println("\nSimple Moving Average (First 10)")
    sma.take(10).foreach(println)

    println("\nWeighted Moving Average (First 10)")
    wma.take(10).foreach(println)

    println("\nExponential Moving Average (First 10)")
    ema.take(10).foreach(println)
  }
}
