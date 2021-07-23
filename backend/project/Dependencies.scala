import sbt.Keys.libraryDependencies
import sbt._
object Dependencies {

  object Version {
    val akkahttpcirce = "1.31.0"
    val scalatest = "3.2.0"
    val mockito = "1.14.3"
    val circe = "0.12.3"
    val typesafeakka = "2.6.12"
    val akkahttp = "10.2.1"
    val pureconfig = "0.12.3"
    val logback = "1.2.3"
    val scoverageVersion = "1.6.1"
    val Gatling = "3.6.1"
  }

  object Libraries {
    val akkahttpcirce: ModuleID =
      "de.heikoseeberger" %% "akka-http-circe" % Version.akkahttpcirce
    val scalatest: ModuleID =
      "org.scalatest" %% "scalatest" % Version.scalatest % Test
    val mockito: ModuleID =
      "org.mockito" %% "mockito-scala-scalatest" % Version.mockito % Test
    val circeparser: ModuleID = "io.circe" %% "circe-parser" % Version.circe
    val circecore: ModuleID = "io.circe" %% "circe-core" % Version.circe
    val circegeneric: ModuleID = "io.circe" %% "circe-generic" % Version.circe
    val akkastream: ModuleID =
      "com.typesafe.akka" %% "akka-stream" % Version.typesafeakka
    val akkahttp: ModuleID =
      "com.typesafe.akka" %% "akka-http" % Version.akkahttp
    val pureconfig: ModuleID =
      "com.github.pureconfig" %% "pureconfig" % Version.pureconfig
    val logback: ModuleID =
      "ch.qos.logback" % "logback-classic" % Version.logback
    val akkatestkit: ModuleID =
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % Version.typesafeakka % Test
    val akkatyped: ModuleID =
      "com.typesafe.akka" %% "akka-actor-typed" % Version.typesafeakka % Test
    val akkahttptestkit: ModuleID =
      "com.typesafe.akka" %% "akka-http-testkit" % Version.akkahttp % Test
    val scoverage = "org.scoverage" % "sbt-scoverage" % Version.scoverageVersion
    val GatlingCharts =
      "io.gatling.highcharts" % "gatling-charts-highcharts" % Version.Gatling % Test
    val Gatling =
      "io.gatling" % "gatling-test-framework" % Version.Gatling % Test
  }

  val testDependencies = Seq(
    Libraries.scalatest,
    Libraries.mockito,
    Libraries.akkatestkit,
    Libraries.akkatyped,
    Libraries.akkahttptestkit,
    Libraries.Gatling,
    Libraries.GatlingCharts
  )

  val prodDependencies = Seq(
    Libraries.akkahttpcirce,
    Libraries.circeparser,
    Libraries.circecore,
    Libraries.circegeneric,
    Libraries.akkastream,
    Libraries.akkahttp,
    Libraries.pureconfig,
    Libraries.logback
  )

  val allDeps = libraryDependencies ++= (prodDependencies ++ testDependencies)
}
