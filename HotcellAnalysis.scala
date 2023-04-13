package cse512

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.udf
import org.apache.spark.sql.functions._

object HotcellAnalysis {
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  Logger.getLogger("org.apache").setLevel(Level.WARN)
  Logger.getLogger("akka").setLevel(Level.WARN)
  Logger.getLogger("com").setLevel(Level.WARN)

def runHotcellAnalysis(spark: SparkSession, pointPath: String): DataFrame =
{
  // Load the original data from a data source
  var pickupInfo = spark.read.format("com.databricks.spark.csv").option("delimiter",";").option("header","false").load(pointPath);
  pickupInfo.createOrReplaceTempView("nyctaxitrips")
  pickupInfo.show()

  // Assign cell coordinates based on pickup points
  spark.udf.register("CalculateX",(pickupPoint: String)=>((
    HotcellUtils.CalculateCoordinate(pickupPoint, 0)
    )))
  spark.udf.register("CalculateY",(pickupPoint: String)=>((
    HotcellUtils.CalculateCoordinate(pickupPoint, 1)
    )))
  spark.udf.register("CalculateZ",(pickupTime: String)=>((
    HotcellUtils.CalculateCoordinate(pickupTime, 2)
    )))
  pickupInfo = spark.sql("select CalculateX(nyctaxitrips._c5),CalculateY(nyctaxitrips._c5), CalculateZ(nyctaxitrips._c1) from nyctaxitrips")
  var newCoordinateName = Seq("x", "y", "z")
  pickupInfo = pickupInfo.toDF(newCoordinateName:_*)
  pickupInfo.show()

  // Define the min and max of x, y, z
  val minX = -74.50/HotcellUtils.coordinateStep
  val maxX = -73.70/HotcellUtils.coordinateStep
  val minY = 40.50/HotcellUtils.coordinateStep
  val maxY = 40.90/HotcellUtils.coordinateStep
  val minZ = 1
  val maxZ = 31
  val numCells = (maxX - minX + 1)*(maxY - minY + 1)*(maxZ - minZ + 1)

  // YOU NEED TO CHANGE THIS PART
  pickupInfo.createOrReplaceTempView("total_points")

  val valid_points = spark.sql("select x, y, z, count(*) as total_count from total_points where x >= " + minX + " and x <= " + maxX +
    " and y >= " + minY + " and y <= " + maxY + " and z >= " + minZ + " and z <= " + maxZ + " group by x, y, z")

  valid_points.createOrReplaceTempView("metrics")
  val metrics_df = spark.sql("select sum(total_count), sum(total_count*total_count) from metrics").first()

  val total_count = metrics_df.get(0).toString.toDouble
  val total_count_square = metrics_df.get(1).toString.toDouble

  val mean: Double = total_count / numCells
  val std: Double = Math.sqrt((total_count_square / numCells) - mean * mean)

  val joinDF = spark.sql("select df1.x as x1, df1.y as y1, df1.z as z1, count(*) as total_neighbours, sum(df2.total_count) as summation from metrics as df1, metrics as df2 WHERE abs(df1.x - df2.x) <= 1 AND abs(df1.y - df2.y) <= 1 AND abs(df1.z - df2.z) <= 1 group by df1.x, df1.y, df1.z")

  spark.udf.register("zscore_funct", (mean: Double, std: Double, total_neighbours: Int, summation: Int, numCells: Int) => ((HotcellUtils.calculateZscore(mean, std, total_neighbours, summation, numCells))))

  joinDF.createOrReplaceTempView("zscore_table")

  val zscore_df = spark.sql("select x1,y1,z1,zscore_funct(" + mean + "," + std + ",total_neighbours,summation," + numCells + ") as zscore from zscore_table")
  zscore_df.createOrReplaceTempView("final_zscore")

  val result_df = spark.sql("select x1,y1,z1 from final_zscore order by zscore desc")
  return result_df
}
}
