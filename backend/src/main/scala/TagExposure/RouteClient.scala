package TagExposure

import java.io.File

import CommonModels.NodePair
import FileIngestion.LocalFileConsumer
import TagGeneration.{EdgeIdentifier, NodeIdentifier}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}

class RouteClient extends Directives {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  def route: Route =
    Route.seal(
      path("local-link") {
        EdgeIdentifier.singleEdge(getLocalTags) match {
          case (taggedFiles) => complete(taggedFiles)
          case (e) => complete(StatusCodes.NotFound -> e)
        }
      }
    )

  def getLocalTags: List[NodePair] = {
    val allLocalFiles: Option[List[File]] = LocalFileConsumer.listFiles("src/test/Resources")
    val filterFiles = LocalFileConsumer.filterFiles(allLocalFiles.get, List(".txt"))
    NodeIdentifier.createNodePairs(filterFiles)
  }
}

object RouteClient {
  def apply(): RouteClient =
    new RouteClient
}
