package com.ikempf.treasurehunt.io

import com.ikempf.treasurehunt.model.Adventurer
import com.ikempf.treasurehunt.model.Assignment
import com.ikempf.treasurehunt.model.Instruction.Advance
import com.ikempf.treasurehunt.model.Instruction.TurnLeft
import com.ikempf.treasurehunt.model.Instruction.TurnRight
import com.ikempf.treasurehunt.model.Mountain
import com.ikempf.treasurehunt.model.Orientation.East
import com.ikempf.treasurehunt.model.Orientation.North
import com.ikempf.treasurehunt.model.Orientation.South
import com.ikempf.treasurehunt.model.Orientation.West
import com.ikempf.treasurehunt.model.Position
import com.ikempf.treasurehunt.model.Treasure
import com.ikempf.treasurehunt.model.TreasureHunt
import com.ikempf.treasurehunt.model.TreasureMap
import fastparse.all._

object Parser {

  private val num     = P(CharIn('0' to '9')).!.map(_.toInt)
  private val name    = P(CharIn('A' to 'z')).rep(1).!
  private val sep     = P(" - ")
  private val newLine = P("\n")

  private val pos = (num ~ sep ~ num).map((Position.apply _).tupled)
  private val orient = P("N" | "E" | "S" | "W").!.map(OrientationFormat.strToOrientation)
  private val instruction = P("A" | "G" | "D").!.map {
    case "A" => Advance
    case "G" => TurnLeft
    case "D" => TurnRight
  }

  private val treasureMap = (P("C") ~ sep ~ num ~ sep ~ num).map((TreasureMap.apply _).tupled)
  private val mountain    = (P("M") ~ sep ~ pos).map(Mountain.apply)
  private val treasure    = (P("T") ~ sep ~ pos ~ sep ~ num).map((Treasure.apply _).tupled)
  private val adventurer  = (name ~ sep ~ pos ~ sep ~ orient).map((Adventurer.apply _).tupled)
  private val assignment  = (P("A") ~ sep ~ adventurer ~ sep ~ instruction.rep(1).map(_.toList)).map((Assignment.apply _).tupled)

  private val mountains   = (mountain ~ newLine).rep(1).map(_.toList)
  private val treasures   = (treasure ~ newLine).rep(1).map(_.toList)
  private val assignments = (assignment ~ newLine).rep(1).map(_.toList)

  private val spec = Start ~ treasureMap ~ newLine ~ mountains ~ treasures ~ assignments ~ End

  def parse(input: String): TreasureHunt =
    spec.map((TreasureHunt.apply _).tupled).parse(input).get.value

}
