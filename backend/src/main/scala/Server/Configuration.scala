package Server

import FileIngestion.LocalFileConsumer
import NetExposure.RouteClient
import NetGeneration.TextGraphCreator
import pureconfig.ConfigSource
import pureconfig.generic.auto._

import scala.concurrent.duration.Duration

object Configuration extends ActorSystemConfig {

  case class ApplicationConfig(interface: String, port: Int, serverSetupTimeout: Duration)
  case class ZetConfig(application: ApplicationConfig)

  val appConfig: ZetConfig = ConfigSource.default.loadOrThrow[ZetConfig]

  val graphCreator = new TextGraphCreator(LocalFileConsumer, "src/test/Resources")
  val allRoutes = new RouteClient(graphCreator)
}
