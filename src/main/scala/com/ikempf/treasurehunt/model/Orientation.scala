package com.ikempf.treasurehunt.model

sealed trait Orientation {
  val iVectorWeight: Int
  val jVectorWeight: Int
  def relativeRight: Orientation = Orientation.relativeRight(this)
  def relativeLeft: Orientation  = Orientation.relativeLeft(this)
}

object Orientation {

  case object North extends Orientation {
    val iVectorWeight = 0
    val jVectorWeight = -1
  }
  case object East extends Orientation {
    val iVectorWeight = 1
    val jVectorWeight = 0
  }
  case object South extends Orientation {
    val iVectorWeight = 0
    val jVectorWeight = 1
  }
  case object West extends Orientation {
    val iVectorWeight = -1
    val jVectorWeight = 0
  }

  val values = List(North, East, South, West)

  def relativeRight(orientation: Orientation): Orientation = {
    val nextIndex = values.indexOf(orientation) + 1
    values(nextIndex % values.length)
  }

  def relativeLeft(orientation: Orientation): Orientation = {
    val previousIndex = values.indexOf(orientation) - 1 + values.length
    val circularIndex = previousIndex + values.length
    values(circularIndex % values.length)
  }

}
