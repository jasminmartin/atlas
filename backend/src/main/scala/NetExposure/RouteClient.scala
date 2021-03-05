package NetExposure

import CommonModels.{FileBody, Graph}
import FileIngestion.LocalFileConsumer
import TagGeneration.{EdgeIdentifier, NodeIdentifier}
import akka.http.scaladsl.server.{Directives, Route}

import scala.io.Source

class RouteClient extends Directives with CorsHandler {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  def route: Route = {
    Route.seal(
      concat(
        corsHandler(
          (
            path("local-link") {
              complete(getNodesAndEdges)
            }
          ) ~
            corsHandler(
              path("file-body" / Segment) { fileName =>
                complete(getFileBody(fileName))
              }
            )
        )
      )
    )
  }

  def getLocalFiles = {
    LocalFileConsumer.getLocalFiles("src/test/Resources", List(".txt"))
  }

  def getFileBody(fileName: String): FileBody = {
    getLocalFiles.filter(_.getName == fileName).head
    val foundFile =
      getLocalFiles.filter(_.getName == fileName).head
    val bufferFile = Source.fromFile(foundFile)
    val stringFile = bufferFile.mkString
    bufferFile.close()
    FileBody(foundFile.getName, stringFile)
  }

  def getNodesAndEdges: Graph = {
    val nodes = NodeIdentifier.findAllFileNodes(getLocalFiles)
    val edges =
      EdgeIdentifier.singleEdge(NodeIdentifier.createNodePairs(getLocalFiles))
    Graph(nodes, edges)
  }
}

object RouteClient {
  def apply(): RouteClient =
    new RouteClient
}
