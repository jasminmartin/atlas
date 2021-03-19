package NetExposure

import NetGeneration.GraphCreator
import akka.http.scaladsl.server.{Directives, Route}

class RouteClient extends Directives with CorsHandler {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  def route: Route = {
    Route.seal(
      corsHandler(
        concat(
          path("local-link") {
            complete(GraphCreator.combineNodesAndEdges)
          }
            ~
              path("file-body" / Segment) { fileName =>
                complete(GraphCreator.getFileBody(fileName))
              }
        )
      )
    )
  }
}

object RouteClient {
  def apply(): RouteClient =
    new RouteClient
}
