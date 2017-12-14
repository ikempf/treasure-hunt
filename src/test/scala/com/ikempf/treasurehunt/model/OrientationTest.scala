package com.ikempf.treasurehunt.model

import com.ikempf.treasurehunt.model.Orientation.East
import com.ikempf.treasurehunt.model.Orientation.North
import com.ikempf.treasurehunt.model.Orientation.South
import com.ikempf.treasurehunt.model.Orientation.West
import com.ikempf.treasurehunt.model.Orientation.relativeRight
import com.ikempf.treasurehunt.model.Orientation.relativeLeft
import org.scalacheck.Gen
import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalatest.prop.PropertyChecks

class OrientationTest extends FlatSpec with Matchers with PropertyChecks {

  "RelativeRight" should "find orientation to the right of the given orientatin" in {
    relativeRight(North) should equal(East)
    relativeRight(West) should equal(North)
  }

  "RelativeLeft" should "find orientation to the left of the given orientatin" in {
    relativeLeft(South) should equal(East)
    relativeLeft(North) should equal(West)
  }

  "Turning" should "always give a different orientation for all orientations" in {
    forAll(Gen.oneOf(Orientation.values)) { orientation =>
      relativeRight(orientation) should not equal orientation
      relativeLeft(orientation) should not equal orientation
    }
  }

}
