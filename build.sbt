import Dependencies._

lazy val treasureHunt = (project in file("."))
  .settings(
    inThisBuild(List(
      organization := "com.ikempt",
      scalaVersion := "2.12.4",
      version := "0.1.0-SNAPSHOT"
    )),
    name := "Treasure hunt",
    scalacOptions += "-Ypartial-unification",
    libraryDependencies ++= List(
      cats,
      fastparse,
      monocleCore,
      monocleMacro,
      scalaTest % Test,
      scalaCheck % Test
    ),
    addCompilerPlugin("org.scalamacros" %% "paradise" % "2.1.0" cross CrossVersion.full)
  )
