package com.ikempf.treasurehunt.io

import com.ikempf.treasurehunt.model.Adventurer
import com.ikempf.treasurehunt.model.AssignmentResult
import com.ikempf.treasurehunt.model.Mountain
import com.ikempf.treasurehunt.model.Orientation.North
import com.ikempf.treasurehunt.model.Position
import com.ikempf.treasurehunt.model.Treasure
import com.ikempf.treasurehunt.model.TreasureHunt
import com.ikempf.treasurehunt.model.TreasureMap
import org.scalatest.FlatSpec
import org.scalatest.Matchers

class PrinterTest extends FlatSpec with Matchers {

  "Format" should "pretty print results" in {
    // Given
    val map        = TreasureMap(3, 4)
    val mountains  = List(Mountain(Position(3, 2)))
    val treasures  = List(Treasure(Position(1, 2), 5))
    val adventurer = Adventurer("adventurer", Position(0, 2), North)
    val hunt       = TreasureHunt(map, mountains, treasures, List.empty)
    val results    = List(AssignmentResult(adventurer, 3))

    // When
    val format = Printer.displayResults(hunt, results)

    // Then
    format should equal("""C - 4 - 3
                          |M - 3 - 2
                          |T - 1 - 2 - 5
                          |A - adventurer - 0 - 2 - N - 3
                          |""".stripMargin)
  }

}
