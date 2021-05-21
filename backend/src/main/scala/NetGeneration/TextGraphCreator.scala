package NetGeneration

import CommonModels.{FileAndNodes, FileBody, Graph}
import FileIngestion.{FileConsumer, LocalFileConsumer}
import NetGeneration.NodeIdentifier.findTaggedNodes
import java.io.{BufferedWriter, File, FileWriter}
import scala.io.Source

class TextGraphCreator(fileConsumer: FileConsumer, fileSource: String)
    extends GraphCreator {

  override def createGraph: Graph = {
    val nodes = findAllFileNodes(
      fileConsumer.getFiles(fileSource, List(".txt"))
    )
    val edges =
      EdgeIdentifier.createEdges(
        createNodePairs(
          LocalFileConsumer.getFiles(fileSource, List(".txt"))
        )
      )
    Graph(nodes, edges)
  }

  private def sanitizeFiles(fileNames: String): String = {
    fileNames.replace(".txt", "")
  }

  def findAllFileNodes(allFiles: List[File]): List[String] = {
    for {
    taggedNodes <- allFiles.map(file => findTaggedNodes(file))
    fileNodes = allFiles.map(file => sanitizeFiles(file.getName))
    allNodes <- fileNodes ++ taggedNodes
    } yield allNodes
  }

  def createNodePairs(allFiles: List[File]): List[FileAndNodes] = {
    allFiles.map(file =>
      FileAndNodes(sanitizeFiles(file.getName), findTaggedNodes(file))
    )
  }

  def getFileBody(fileName: String): Option[FileBody] = {
    val nameWithExtension = fileName + ".txt"
    LocalFileConsumer
      .getFiles("src/test/Resources/TestData", List(".txt"))
      .find(_.getName == nameWithExtension)
      .map(file => {
        val bufferFile = Source.fromFile(file)
        val stringFile = bufferFile.mkString
        bufferFile.close()
        FileBody(fileName, stringFile.trim())
      })
  }

  def updateFileBody(fileName: String, body: String): Graph = {
    val nameWithExtension = fileName + ".txt"
    LocalFileConsumer
      .getFiles("src/test/Resources/TestData", List(".txt"))
      .find(_.getName == nameWithExtension)
      .foreach(file => {
        val bw = new BufferedWriter(new FileWriter(file))
        bw.write(body)
        bw.close()
      })
    createGraph
  }
}
