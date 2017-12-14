package com.ikempf.treasurehunt.io

import com.ikempf.treasurehunt.model.AssignmentResult
import com.ikempf.treasurehunt.model.Mountain
import com.ikempf.treasurehunt.model.Orientation
import com.ikempf.treasurehunt.model.Treasure
import com.ikempf.treasurehunt.model.TreasureHunt
import com.ikempf.treasurehunt.model.TreasureMap

object Printer {

  def displayResults(treasureHunt: TreasureHunt, results: List[AssignmentResult]): String = {
    val map         = displayMap(treasureHunt.treasureMap)
    val mountains   = treasureHunt.mountains.map(displayMountain)
    val treasures   = treasureHunt.treasures.map(displayTreasure)
    val assignments = results.map(displayAssignment)

    (List(map) ++ mountains ++ treasures ++ assignments).mkString("\n") + "\n"
  }

  private def displayMap(treasureMap: TreasureMap) =
    s"C - ${treasureMap.height} - ${treasureMap.width}"

  private def displayMountain(mountain: Mountain) =
    s"M - ${mountain.pos.i} - ${mountain.pos.j}"

  private def displayTreasure(treasure: Treasure) =
    s"T - ${treasure.pos.i} - ${treasure.pos.j} - ${treasure.count}"

  private def displayAssignment(assignmentResult: AssignmentResult) = {
    val adventurer    = assignmentResult.adventurer
    val pos           = adventurer.position
    val name          = adventurer.name
    val orientation   = adventurer.orientation
    val treasureCount = assignmentResult.treasureCount

    s"A - $name - ${pos.i} - ${pos.j} - ${displayOrientation(orientation)} - $treasureCount"
  }

  private def displayOrientation(orientation: Orientation) =
    OrientationFormat.orientationToStr(orientation)

}
