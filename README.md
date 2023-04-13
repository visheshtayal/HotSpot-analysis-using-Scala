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

1. The input will be a monthly taxi trip dataset from 2009 - 2012. For example, "yellow_tripdata_2009-01_point.csv", "yellow_tripdata_2010-02_point.csv".

2. Each cell unit size is 0.01 * 0.01 in terms of latitude and longitude degrees.

3. You should use 1 day as the Time Step size. The first day of a month is step 1. Every month has 31 days.

4. You only need to consider Pick-up Location.

5. We don't use Jaccard similarity to check your answer. However, you don't need to worry about how to decide the cell coordinates because the code template generated cell coordinates. You just need to write the rest of the task.

Coding template specification
Input parameters
Output path (Mandatory)

Task name: "hotzoneanalysis" or "hotcellanalysis"

Task parameters: (1) Hot zone (2 parameters): nyc taxi data path, zone path(2) Hot cell (1 parameter): nyc taxi data path

Example:

test/output hotzoneanalysis src/resources/point-hotzone.csv src/resources/zone-hotzone.csv hotcellanalysis src/resources/yellow_tripdata_2009-01_point.csv

#Input data format

1. Point data: the input point dataset is the pickup point of New York Taxi trip datasets. The data format of this phase is the original format of NYC taxi trip which is different from Phase 2. But the coding template already parsed it for you. Find the data in the .zip file below.

yellow_tripdata_2009-01_point.csv

2. Zone data (only for hot zone analysis): at "src/resources/zone-hotzone" of the template

Hot zone analysis

The input point data can be any small subset of NYC taxi dataset.

#Hot cell analysis

The input point data is a monthly NYC taxi trip dataset (2009-2012) like "yellow_tripdata_2009-01_point.csv"

#Output data format

Hot zone analysis

All zones with their count, sorted by "rectangle" string in an ascending order.

Hot cell analysis

The coordinates of top 50 hotest cells sorted by their G score in a descending order. Note, DO NOT OUTPUT G score.

-7399,4075,15
-7399,4075,29
-7399,4075,22
