package com.ikempf.treasurehunt.model

case class TreasureMap(width: Int, height: Int) {

  def inBoundaries(position: Position): Boolean =
    position.i >= 0 &&
      position.j >= 0 &&
      position.i < width &&
      position.j < height

}
