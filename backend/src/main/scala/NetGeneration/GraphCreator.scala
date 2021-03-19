package NetGeneration

import CommonModels.{FileBody, Graph}
import FileIngestion.LocalFileConsumer

import scala.io.Source

object GraphCreator {
  def combineNodesAndEdges: Graph = {
    val nodes = NodeIdentifier.findAllFileNodes(
      LocalFileConsumer.getLocalFiles("src/test/Resources", List(".txt"))
    )
    val edges =
      EdgeIdentifier.singleEdge(
        NodeIdentifier.createNodePairs(
          LocalFileConsumer.getLocalFiles("src/test/Resources", List(".txt"))
        )
      )
    Graph(nodes, edges)
  }

  def sanitizeFiles(fileNames: String): String = {
    fileNames.replace(".txt", "")
  }

  def getFileBody(fileName: String): FileBody = {
    val nameWithExtension = fileName + ".txt"
    val foundFile = LocalFileConsumer
      .getLocalFiles("src/test/Resources", List(".txt"))
      .filter(_.getName == nameWithExtension).head
    val bufferFile = Source.fromFile(foundFile)
    val stringFile = bufferFile.mkString
    bufferFile.close()
    FileBody(fileName, stringFile.trim())
  }
}

