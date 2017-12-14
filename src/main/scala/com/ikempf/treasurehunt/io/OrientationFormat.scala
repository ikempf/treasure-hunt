package com.ikempf.treasurehunt.io

import com.ikempf.treasurehunt.model.Orientation
import com.ikempf.treasurehunt.model.Orientation.East
import com.ikempf.treasurehunt.model.Orientation.North
import com.ikempf.treasurehunt.model.Orientation.South
import com.ikempf.treasurehunt.model.Orientation.West

object OrientationFormat {

  private [io] val orientationToStr: Map[Orientation, String] =
    Map(
      North -> "N",
      East  -> "E",
      South -> "S",
      West  -> "W"
    )

  private [io] val strToOrientation: Map[String, Orientation] =
    orientationToStr.map(_.swap)

}
