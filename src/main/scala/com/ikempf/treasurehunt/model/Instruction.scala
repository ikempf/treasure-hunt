package com.ikempf.treasurehunt.model

sealed trait Instruction

object Instruction {

  case object Advance   extends Instruction
  case object TurnLeft  extends Instruction
  case object TurnRight extends Instruction

}
