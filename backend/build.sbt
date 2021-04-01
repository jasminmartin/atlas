import Dependencies._

name := "zettelkasten"

version := "0.1"

scalaVersion := "2.13.3"

lazy val zettelkasten = Project(id = "zettlekasten", base = file("."))
  .settings(allDeps)

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
enablePlugins(DockerComposePlugin)

mainClass in Compile := Some("Server.HttpServer")
dockerExposedPorts := Seq(4024)
dockerImageCreationTask := (publishLocal in Docker).value
