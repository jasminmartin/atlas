name := "zettlekasten"

version := "0.1"

scalaVersion := "2.13.3"

val ScalaTest = "3.2.0"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.0" % "test",
  "org.mockito" %% "mockito-scala-scalatest" % "1.14.3",
  "org.scalactic" %% "scalactic" % "3.2.0"
)
