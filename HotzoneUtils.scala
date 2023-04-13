package cse512

object HotzoneUtils {

  def ST_Contains(queryRectangle: String, pointString: String ): Boolean = {

    var boundary_boxes = queryRectangle.split(',').map(_.toDouble)
    var point_coordinates = pointString.split(',').map(_.toDouble)
    return (point_coordinates(0) >= boundary_boxes(0) && point_coordinates(0) <= boundary_boxes(2) && point_coordinates(1) >= boundary_boxes(1) && point_coordinates(1) <= boundary_boxes(3)) ||
      (point_coordinates(0) >= boundary_boxes(2) && point_coordinates(0) <= boundary_boxes(0) && point_coordinates(1) >= boundary_boxes(3) && point_coordinates(1) <= boundary_boxes(1))
  }
}
