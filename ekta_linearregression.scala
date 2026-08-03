import scala.io.Source
import breeze.linalg._
import breeze.stats.regression._


import org.knowm.xchart._
import org.knowm.xchart.SwingWrapper

import scala.collection.JavaConverters._

object LinearRegressionExample {

  def main(args: Array[String]): Unit = {

    // Reading dataset
    val data = Source.fromInputStream(
        getClass.getResourceAsStream("/salary.csv")
      )
      .getLines()
      .drop(1)
      .map(_.split(","))
      .toArray


    val xValues = data.map(row => row(0).toDouble)   // age
    val yValues = data.map(row => row(3).toDouble)   // hours.per.week

    val x = DenseVector(xValues)
    val y = DenseVector(yValues)


    // Creating Design Matrix
    val ones = DenseVector.ones[Double](x.length)

    val X = DenseMatrix.horzcat(
      ones.asDenseMatrix.t,
      x.asDenseMatrix.t
    )


    // Linear Regression
    val result = leastSquares(X, y)

    val coefficients = result.coefficients



    println("Linear Regression Model")
    println("-----------------------")

    println("Intercept : " + coefficients(0))
    println("Slope     : " + coefficients(1))


    // Prediction
    val newExperience = 6.0

    val predictedSalary =
      coefficients(0) + coefficients(1) * newExperience


    println("\nPrediction")
    println("-----------------------")
    println("Experience = " + newExperience)
    println("Predicted Salary = " + predictedSalary)





    // -------- GRAPH ---------

    val chart = new XYChartBuilder()
      .width(800)
      .height(600)
      .title("Linear Regression")
      .xAxisTitle("Age")
      .yAxisTitle("Hours per Week")
      .build()


    // Actual data points
    chart.addSeries(
      "Actual Data",
      xValues,
      yValues
    )


    // Regression line values

    val lineX = Array(
      xValues.min,
      xValues.max
    )

    val lineY = lineX.map(value =>
      coefficients(0) + coefficients(1) * value
    )


    chart.addSeries(
      "Regression Line",
      lineX,
      lineY
    )


    // Display graph
    new SwingWrapper(chart).displayChart()

  }
}

