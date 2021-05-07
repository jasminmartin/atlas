package NetExposure

import CommonModels.FileBody
import NetGeneration.TextGraphCreator
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}

class RouteClient(graphCreator: TextGraphCreator)
    extends Directives
    with CorsHandler {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  def route: Route = {
    Route.seal(
      corsHandler(
        concat(
          (get & path("local-link")) {
            complete(graphCreator.createGraph)
          }
            ~
            (get & path("file-body" / Segment)) { fileName =>
                complete(graphCreator.getFileBody(fileName) match {
                  case Some(file) => file
                  case None       => StatusCodes.NotFound
                })
              }
          ~
            (put & path("file-body" / Segment)) { fileName =>
              entity(as[FileBody]) { request =>
                complete(graphCreator.updateFileBody(fileName, request.body))
              }
            }
        )
      )
    )
  }
}
