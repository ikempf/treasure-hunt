package com.ikempf.treasurehunt.parsing

import com.ikempf.treasurehunt.io.Parser
import com.ikempf.treasurehunt.model.Adventurer
import com.ikempf.treasurehunt.model.Assignment
import com.ikempf.treasurehunt.model.Instruction.Advance
import com.ikempf.treasurehunt.model.Instruction.TurnLeft
import com.ikempf.treasurehunt.model.Instruction.TurnRight
import com.ikempf.treasurehunt.model.Mountain
import com.ikempf.treasurehunt.model.Orientation.South
import com.ikempf.treasurehunt.model.Position
import com.ikempf.treasurehunt.model.Treasure
import com.ikempf.treasurehunt.model.TreasureHunt
import com.ikempf.treasurehunt.model.TreasureMap
import org.scalatest.FlatSpec
import org.scalatest.Matchers

class ParserTest extends FlatSpec with Matchers {

  "Parser" should "parse treasure hunting job" in {
    // Given
    val input =
      """C - 3 - 4
        |M - 1 - 0
        |M - 2 - 1
        |T - 0 - 3 - 2
        |T - 1 - 3 - 3
        |A - lara - 1 - 3 - S - AADADAGGA
        |""".stripMargin

    // When
    val hunt = Parser.parse(input)

    // Then
    val instructions = List(Advance, Advance, TurnRight, Advance, TurnRight, Advance, TurnLeft, TurnLeft, Advance)
    val adventurer = Adventurer("lara", Position(1, 3), South)

    hunt should equal(
      TreasureHunt(
        TreasureMap(3, 4),
        List(Mountain(Position(1, 0)), Mountain(Position(2, 1))),
        List(Treasure(Position(0, 3), 2), Treasure(Position(1, 3), 3)),
        List(Assignment(adventurer, instructions))
      ))

  }

}
