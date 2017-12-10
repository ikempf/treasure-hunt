import Dependencies._

lazy val treasureHunt = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.ikempt",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Treasure hunt",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += scalaCheck % Test
  )
