import sbt._

object Dependencies {
  lazy val scalaTest    = "org.scalatest"              %% "scalatest"     % "3.0.3"
  lazy val scalaCheck   = "org.scalacheck"             %% "scalacheck"    % "1.13.4"
  lazy val cats         = "org.typelevel"              %% "cats-core"     % "1.0.0-RC1"
  lazy val fastparse    = "com.lihaoyi"                %% "fastparse"     % "1.0.0"
  lazy val monocleCore  = "com.github.julien-truffaut" %% "monocle-core"  % "1.5.0-cats-M2"
  lazy val monocleMacro = "com.github.julien-truffaut" %% "monocle-macro" % "1.5.0-cats-M2"

}
