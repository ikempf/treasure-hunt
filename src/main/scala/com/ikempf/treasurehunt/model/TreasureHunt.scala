package com.ikempf.treasurehunt.model

import scala.collection.immutable.Seq

case class TreasureHunt(treasureMap: TreasureMap,
                        mountains: Seq[Mountain],
                        treasures: Seq[Treasure],
                        assignments: Seq[Assignment]) {

  def positionAllowed(pos: Position): Boolean =
    treasureMap.inBoundaries(pos) && !occupiedByMountain(pos)

  private def occupiedByMountain(pos: Position): Boolean =
    mountains
      .map(_.pos)
      .contains(pos)

}
