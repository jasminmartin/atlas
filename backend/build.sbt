name := "zettelkasten"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "de.heikoseeberger" %% "akka-http-circe" % "1.31.0",
  "org.scalatest" %% "scalatest" % "3.2.0" % "test",
  "org.mockito" %% "mockito-scala-scalatest" % "1.14.3" % "test",
  "io.circe" %% "circe-parser" % "0.12.3",
  "io.circe" %% "circe-core" % "0.12.3",
  "io.circe" %% "circe-generic" % "0.12.3",
  "com.typesafe.akka" %% "akka-stream" % "2.6.12",
  "com.typesafe.akka" %% "akka-http" % "10.2.1",
  "com.github.pureconfig" %% "pureconfig" % "0.12.3",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % "2.6.12" % Test,
  "com.typesafe.akka"         %% "akka-actor-typed" % "2.6.12" % Test,
  "com.typesafe.akka" %% "akka-http-testkit" %  "10.2.1" % Test
)
