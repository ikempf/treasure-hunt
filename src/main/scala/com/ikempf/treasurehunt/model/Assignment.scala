package com.ikempf.treasurehunt.model

import scala.collection.immutable.Seq

case class Assignment(adventurer: Adventurer, instructions: Seq[Instruction])
