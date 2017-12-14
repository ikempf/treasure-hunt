package com.ikempf.treasurehunt.service

import com.ikempf.treasurehunt.service.TreasureHunting.advance
import com.ikempf.treasurehunt.service.TreasureHunting.moveTowards
import com.ikempf.treasurehunt.service.TreasureHunting.start
import com.ikempf.treasurehunt.model.Adventurer
import com.ikempf.treasurehunt.model.Assignment
import com.ikempf.treasurehunt.model.AssignmentResult
import com.ikempf.treasurehunt.model.Instruction.Advance
import com.ikempf.treasurehunt.model.Instruction.TurnLeft
import com.ikempf.treasurehunt.model.Instruction.TurnRight
import com.ikempf.treasurehunt.model.Mountain
import com.ikempf.treasurehunt.model.Orientation.East
import com.ikempf.treasurehunt.model.Orientation.North
import com.ikempf.treasurehunt.model.Orientation.South
import com.ikempf.treasurehunt.model.Position
import com.ikempf.treasurehunt.model.Treasure
import com.ikempf.treasurehunt.model.TreasureHunt
import com.ikempf.treasurehunt.model.TreasureMap
import org.scalatest.FlatSpec
import org.scalatest.Matchers

import scala.collection.immutable
import scala.collection.immutable.Seq

class TreasureHuntingTest extends FlatSpec with Matchers {

  "MoveTowards" should "move in current orientation" in {
    moveTowards(Position(0, 0), North) should equal(Position(0, -1))
    moveTowards(Position(0, 3), East) should equal(Position(1, 3))
  }

  "Advance" should "not move outside the map" in {
    // Given
    val hunt       = treasureHunt(5, 5)
    val adventurer = Adventurer("name", Position(1, 5), South)
    val result     = AssignmentResult(adventurer, 0)

    // When
    val res = advance(result).runA(hunt)

    // Then
    res.value should equal(result)
  }

  it should "not move onto mountains" in {
    // Given
    val mountain   = Mountain(Position(1, 2))
    val map        = treasureHunt(5, 5, mountains = List(mountain))
    val adventurer = Adventurer("name", Position(1, 1), South)
    val result     = AssignmentResult(adventurer, 3)

    // When
    val res = advance(result).runA(map)

    // Then
    res.value should equal(result)
  }

  it should "pick up a treasure" in {
    // Given
    val treasure   = Treasure(Position(1, 2), 2)
    val hunt       = treasureHunt(5, 5, treasures = List(treasure))
    val adventurer = Adventurer("name", Position(1, 1), South)
    val result     = AssignmentResult(adventurer, 3)

    // When
    val res = advance(result).runA(hunt)

    // Then
    res.value should equal(AssignmentResult(Adventurer("name", Position(1, 2), South), 4))
  }

  "Move" should "execute all adventurer's instructions" in {
    // Given
    val map          = treasureHunt(5, 5)
    val adventurer   = Adventurer("name", Position(2, 2), South)
    val instructions = List(Advance, TurnLeft, Advance, TurnRight)
    val assignment   = Assignment(adventurer, instructions)

    // When
    val res = TreasureHunting.execute(assignment).runA(map)

    // Then
    res.value should equal(AssignmentResult(Adventurer("name", Position(3, 3), South), 0))
  }

  "StartTreasureHunt" should "run all adventurer's assignments" in {
    // Given
    val mountain1  = Mountain(Position(1, 0))
    val mountain2  = Mountain(Position(2, 1))
    val treasures1 = Treasure(Position(0, 3), 2)
    val treasures2 = Treasure(Position(1, 3), 3)
    val adventurer = Adventurer("Lara", Position(1, 1), South)
    val assignment =
      Assignment(adventurer,
                 List(Advance, Advance, TurnRight, Advance, TurnRight, Advance, TurnLeft, TurnLeft, Advance))

    val hunt = treasureHunt(width = 3,
                            height = 4,
                            mountains = List(mountain1, mountain2),
                            treasures = List(treasures1, treasures2),
                            adventurers = List(assignment))

    // When
    val (finalHunt, assignmentResults) = start(hunt)

    // Then
    finalHunt.treasures should equal(List(Treasure(Position(0, 3), 0), Treasure(Position(1, 3), 2)))
    assignmentResults should equal(List(AssignmentResult(Adventurer("Lara", Position(0, 3), South), 3)))
  }

  private def treasureHunt(height: Int,
                           width: Int,
                           mountains: List[Mountain] = List.empty,
                           treasures: List[Treasure] = List.empty,
                           adventurers: Seq[Assignment] = List.empty) =
    TreasureHunt(TreasureMap(width, height), mountains, treasures, adventurers)

}
