package com.ikempf.treasurehunt.model

import com.ikempf.treasurehunt.model.Treasure.TreasureCount

case class Treasure(pos: Position, count: TreasureCount)

object Treasure {
  type TreasureCount = Int
}
