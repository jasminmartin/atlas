package Server

import pureconfig.ConfigSource
import pureconfig.generic.auto._

import scala.concurrent.duration.Duration

object Configuration extends ActorSystemConfig with InternalDependencyConfig {
  case class ApplicationConfig(interface: String, port: Int, serverSetupTimeout: Duration)
  case class ZetConfig(application: ApplicationConfig)
  case class AppConfig(zet: ZetConfig)
  val appConfig: AppConfig = ConfigSource.default.loadOrThrow[AppConfig]
}
