import Dependencies._

name := "zettelkasten"

version := "0.1"

scalaVersion := "2.13.3"

lazy val zettelkasten = Project(id = "zettlekasten", base = file("."))
  .settings(allDeps)
