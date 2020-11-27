name := "zettlekasten"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.0" % "test",
  "org.mockito" %% "mockito-scala-scalatest" % "1.14.3",
  "org.scalactic" %% "scalactic" % "3.2.0",
  "io.circe" %% "circe-parser" % "0.12.3",
  "io.circe" %% "circe-core" % "0.12.3",
  "io.circe" %% "circe-generic" % "0.12.3",
  "com.typesafe.akka" %% "akka-stream" % "2.6.8",
  "com.typesafe.akka" %% "akka-http" % "10.2.1"
)
