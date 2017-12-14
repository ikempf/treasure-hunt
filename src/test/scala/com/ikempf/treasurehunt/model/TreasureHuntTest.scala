package com.ikempf.treasurehunt.model

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class TreasureHuntTest extends FlatSpec with Matchers {

  "PositionAllowed" should "forbid positions occupied by mountains" in {
    // Given
    val mountain = Mountain(Position(2, 3))
    val map = TreasureHunt(TreasureMap(5, 5), List(mountain), List.empty, List.empty)

    // When / Then
    map.positionAllowed(Position(2,3)) should be(false)
  }

  it should "only allow positions inside of the map" in {
    // Given
    val map = TreasureHunt(TreasureMap(5, 5), List.empty, List.empty, List.empty)

    // When / Then
    map.positionAllowed(Position(-1,3)) should be(false)
    map.positionAllowed(Position(2,6)) should be(false)
    map.positionAllowed(Position(2,4)) should be(true)
  }

}
