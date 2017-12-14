package com.ikempf.treasurehunt.service

import cats.data.State
import com.ikempf.treasurehunt.model.Adventurer
import com.ikempf.treasurehunt.model.Assignment
import com.ikempf.treasurehunt.model.AssignmentResult
import com.ikempf.treasurehunt.model.Instruction
import com.ikempf.treasurehunt.model.Instruction.Advance
import com.ikempf.treasurehunt.model.Instruction.TurnLeft
import com.ikempf.treasurehunt.model.Instruction.TurnRight
import com.ikempf.treasurehunt.model.Orientation
import com.ikempf.treasurehunt.model.Position
import com.ikempf.treasurehunt.model.Treasure
import com.ikempf.treasurehunt.model.TreasureHunt
import cats.syntax.applicative._
import cats.syntax.apply._
import cats.instances.option._

object TreasureHunting {

  type HuntState[A] = State[TreasureHunt, A]

  def start(treasureHunt: TreasureHunt): (TreasureHunt, List[AssignmentResult]) =
    describeHunt(treasureHunt)
      .run(treasureHunt)
      .value

  private def describeHunt(treasureHunt: TreasureHunt): HuntState[List[AssignmentResult]] =
    treasureHunt.assignments
      .foldLeft(State.pure[TreasureHunt, List[AssignmentResult]](List.empty)) { (assignmentResults, assignment) =>
        (execute(assignment), assignmentResults).mapN(_ +: _)
      }

  private[service] def execute(assignment: Assignment): HuntState[AssignmentResult] =
    move(assignment.adventurer, assignment.instructions)

  private def move(adventurer: Adventurer, instructions: Seq[Instruction]): HuntState[AssignmentResult] = {
    val empty = AssignmentResult(adventurer, 0)

    instructions
      .foldLeft(State.pure[TreasureHunt, AssignmentResult](empty)) { (resultAcc, inst) =>
        resultAcc.flatMap(applyInstruction(inst))
      }
  }

  private def applyInstruction(inst: Instruction)(result: AssignmentResult): HuntState[AssignmentResult] =
    inst match {
      case Advance   => advance(result)
      case TurnLeft  => moveLeft(result).pure[HuntState]
      case TurnRight => moveRight(result).pure[HuntState]
    }

  private val orientLens = AssignmentResult.adventurer.composeLens(Adventurer.orientation)

  private def moveLeft(result: AssignmentResult) =
    orientLens.set(result.adventurer.orientation.relativeLeft)(result)

  private def moveRight(result: AssignmentResult) =
    orientLens.set(result.adventurer.orientation.relativeRight)(result)

  private[service] def advance(result: AssignmentResult): HuntState[AssignmentResult] = {
    val adventurer  = result.adventurer
    val newPosition = moveTowards(adventurer.position, adventurer.orientation)

    State { hunt =>
      if (hunt.positionAllowed(newPosition)) {
        val (newHunt, treasuresFound) = collectTreasures(newPosition).run(hunt).value
        val newTreasureCount          = result.treasureCount + treasuresFound
        val newAdventurer             = adventurer.copy(position = newPosition)
        val newResult                 = result.copy(newAdventurer, newTreasureCount)

        (newHunt, newResult)
      } else
        (hunt, result)
    }
  }

  private[service] def moveTowards(position: Position, orientation: Orientation): Position =
    position.copy(
      i = position.i + orientation.iVectorWeight,
      j = position.j + orientation.jVectorWeight
    )

  private val TreasureCollectRate = 1

  private def collectTreasures(pos: Position): HuntState[Int] =
    State { hunt =>
      val treasuresWithoutCollected = hunt.treasures
        .map {
          case Treasure(`pos`, count) => Treasure(pos, count - TreasureCollectRate)
          case treasure               => treasure
        }

      val collectedTreasureCount = hunt.treasures
        .find(_.pos == pos)
        .map(_ => TreasureCollectRate)
        .getOrElse(0)

      val newHunt = hunt.copy(treasures = treasuresWithoutCollected)
      (newHunt, collectedTreasureCount)
    }

}
