# HotSpot-analysis-using-Scala
The problem statement of the project was taken from ACM SIGSPATIAL GISCUP 2016 and focuses on two different hot spot analysis tasks i.e hot zone analysis and hot cell analysis.

1. Hot zone analysis
This task will need to perform a range join operation on a rectangle datasets and a point dataset. For each rectangle, the number of points located within the rectangle will be obtained. The hotter rectangle means that it includes more points. So this task is to calculate the hotness of all the rectangles.

2. Hot cell analysis
Description

This task will focus on applying spatial statistics to spatio-temporal big data in order to identify statistically significant spatial hot spots using Apache Spark. The topic of this task is from ACM SIGSPATIAL GISCUP 2016.

The Problem Definition page is here: http://sigspatial2016.sigspatial.org/giscup2016/problem

The Submit Format page is here: http://sigspatial2016.sigspatial.org/giscup2016/submit

Special requirement (different from GIS CUP)

As stated in the Problem Definition page, in this task, you are asked to implement a Spark program to calculate the Getis-Ord statistic of NYC Taxi Trip datasets. We call it "Hot cell analysis"

To reduce the computation power need, we made the following changes:

1. Each cell unit size is 0.01 * 0.01 in terms of latitude and longitude degrees.

2. You should use 1 day as the Time Step size. The first day of a month is step 1. Every month has 31 days.

3. You only need to consider Pick-up Location.

5. We don't use Jaccard similarity to check your answer. However, you don't need to worry about how to decide the cell coordinates because the code template generated cell coordinates. You just need to write the rest of the task.

You need to change "HotzoneAnalysis.scala and HotzoneUtils.scala".

#How to submit your code to Spark
If you are using the Scala template

Go to project root folder,

Run sbt clean assembly. You may need to install sbt in order to run this command.

Find the packaged jar in "./target/scala-2.11/CSE511-Project-Hotspot-Analysis-Template-assembly-0.1.0.jar"

Submit the jar to Spark using Spark command "./bin/spark-submit". A pseudo code example: ./bin/spark-submit ~/GitHub/CSE511-Project-Hotspot-Analysis-Template/target/scala-2.11/CSE511-Project-Hotspot-Analysis-Template-assembly-0.1.0.jar test/output hotzoneanalysis src/resources/point-hotzone.csv src/resources/zone-hotzone.csv hotcellanalysis src/resources/yellow_tripdata_2009-01_point.csv
