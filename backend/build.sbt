import Dependencies._

name := "atlas"

version := "0.1"


scalaVersion := "2.13.3"
parallelExecution in Test := false

lazy val Atlas = Project(id = "atlas", base = file("."))
  .settings(allDeps)
  .settings(ScoverageSettings.scoverageSettings)

enablePlugins(ScoverageSbtPlugin)
enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
enablePlugins(DockerComposePlugin)

mainClass in Compile := Some("Server.HttpServer")
dockerExposedPorts := Seq(4024)
dockerImageCreationTask := (publishLocal in Docker).value
