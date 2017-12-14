package com.ikempf.treasurehunt.model

import monocle.macros.Lenses

@Lenses
case class Adventurer(name: String, position: Position, orientation: Orientation)
