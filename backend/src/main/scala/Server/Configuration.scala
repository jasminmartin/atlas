package Server

import FileIngestion.LocalFileParser
import NetExposure.RouteClient
import NetGeneration.GraphCreator
import pureconfig.ConfigSource
import pureconfig.generic.auto._

import scala.concurrent.duration.Duration

object Configuration extends ActorSystemConfig {

  case class ApplicationConfig(interface: String, port: Int, serverSetupTimeout: Duration)
  case class AtlasConfig(application: ApplicationConfig)

  val appConfig: AtlasConfig = ConfigSource.default.loadOrThrow[AtlasConfig]

  val graphCreator = new GraphCreator(LocalFileParser, "src/test/Resources/Test/")
  val allRoutes = new RouteClient(graphCreator)
}
