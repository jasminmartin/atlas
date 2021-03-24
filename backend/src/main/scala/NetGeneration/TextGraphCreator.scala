package NetGeneration

import CommonModels.{FileBody, Graph}
import FileIngestion.{FileConsumer, LocalFileConsumer}

import scala.io.Source

class TextGraphCreator(fileConsumer: FileConsumer, fileSource: String) extends GraphCreator {

  override def createGraph: Graph = {
    val nodes = NodeIdentifier.findAllFileNodes(
      fileConsumer.getFiles(fileSource, List(".txt"))
    )
    val edges =
      EdgeIdentifier.createEdges(
        NodeIdentifier.createNodePairs(
          LocalFileConsumer.getFiles(fileSource, List(".txt"))
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
      .getFiles("src/test/Resources", List(".txt"))
      .filter(_.getName == nameWithExtension).head
    val bufferFile = Source.fromFile(foundFile)
    val stringFile = bufferFile.mkString
    bufferFile.close()
    FileBody(fileName, stringFile.trim())
  }
}

