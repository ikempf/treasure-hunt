package com.ikempf.treasurehunt.model

import com.ikempf.treasurehunt.model.Treasure.TreasureCount
import monocle.macros.Lenses

@Lenses
case class AssignmentResult(adventurer: Adventurer, treasureCount: TreasureCount)

