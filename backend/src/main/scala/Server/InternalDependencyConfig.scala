package Server

import NetExposure._

trait InternalDependencyConfig {
  val zetRoutes: RouteClient = RouteClient()
}
