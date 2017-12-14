package com.ikempf

import java.io.File
import java.io.PrintWriter

import com.ikempf.treasurehunt.io.Parser
import com.ikempf.treasurehunt.io.Printer
import com.ikempf.treasurehunt.service.TreasureHunting

import scala.io.Source

object Main extends App {

  private val source       = Source.fromResource("input.txt")
  private val input        = try { source.mkString } finally { source.close() }
  private val treasureHunt = Parser.parse(input)

  private val (updatedHunt, results) = TreasureHunting.start(treasureHunt)

  private val formattedResults = Printer.displayResults(updatedHunt, results)
  private val writer           = new PrintWriter(new File("target/output.txt"))
  try { writer.write(formattedResults) } finally { writer.close() }

}
