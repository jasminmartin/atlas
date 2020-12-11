package Server

import TagExposure._

trait InternalDependencyConfig {
  val zetRoutes: RouteClient = RouteClient()
}
