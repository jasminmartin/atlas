package NetExposure

import java.io.File

import CommonModels.Graph
import FileIngestion.LocalFileConsumer
import TagGeneration.{EdgeIdentifier, NodeIdentifier}
import akka.http.scaladsl.server.{Directives, Route}

class RouteClient extends Directives with CorsHandler {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  def route: Route = {
    Route.seal(
      corsHandler(
        path("local-link") {
          complete(getNodesAndEdges)
        }
      )
    )
  }

  def getNodesAndEdges: Graph = {
    val files: List[File] =
      LocalFileConsumer.getLocalFiles("src/test/Resources", List(".txt"))
    val nodes = NodeIdentifier.findAllFileNodes(files)
    val edges = EdgeIdentifier.singleEdge(NodeIdentifier.createNodePairs(files))
    Graph(nodes, edges)
  }
}

object RouteClient {
  def apply(): RouteClient =
    new RouteClient
}
