package NetExposure

import java.io.File

import CommonModels.{Graph, Edge}
import FileIngestion.LocalFileConsumer
import TagGeneration.{EdgeIdentifier, NodeIdentifier}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}

class RouteClient extends Directives {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  def route: Route = {
        Route.seal(
          path("local-link") {
            complete(getNodesAndEdges)
          }
        )
  }



  def getNodesAndEdges: Graph = {
    val files: List[File] = LocalFileConsumer.getLocalFiles("src/test/Resources", List(".txt"))
    val nodes = NodeIdentifier.findAllFileNodes(files)
    val edges = EdgeIdentifier.singleEdge(NodeIdentifier.createNodePairs(files))
    Graph(nodes, edges)
  }
}

object RouteClient {
  def apply(): RouteClient =
    new RouteClient
}
