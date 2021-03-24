package NetExposure

import NetGeneration.TextGraphCreator
import akka.http.scaladsl.server.{Directives, Route}

class RouteClient(graphCreator: TextGraphCreator) extends Directives with CorsHandler {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  def route: Route = {
    Route.seal(
      corsHandler(
        concat(
          path("local-link") {
            complete(graphCreator.createGraph)
          }
            ~
              path("file-body" / Segment) { fileName =>
                complete(graphCreator.getFileBody(fileName))
              }
        )
      )
    )
  }
}
