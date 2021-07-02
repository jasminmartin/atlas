package NetGeneration

import CommonModels.{Edge, FileAndTags, FileBody, Graph}
import FileIngestion.{FileConsumer, LocalFileConsumer}
import NetGeneration.NodeIdentifier.findTaggedNodes
import NetGeneration.Stemmer.combineStems

import java.io.{BufferedWriter, File, FileWriter}
import java.nio.file.NoSuchFileException
import scala.io.Source
import scala.util.chaining.scalaUtilChainingOps

class GraphCreator(fileConsumer: FileConsumer, fileSource: String) {

  def createGraph: Graph = {
    val nodes: List[String] = findAllFileNodes(
      fileConsumer.getFiles(fileSource, List(".txt"))
    )
    val edges: List[Edge] =
      EdgeIdentifier
        .createEdges(
          createNodePairs(
            LocalFileConsumer.getFiles(fileSource, List(".txt"))
          )
        )
    Graph(nodes.distinct, edges)
  }

  private def sanitizeFiles(fileNames: String): String = {
    fileNames.replace(".txt", "")
  }

  def findAllFileNodes(allFiles: List[File]): List[String] = {
    for {
      taggedNodes <- allFiles.map(file => findTaggedNodes(file))
      fileNodes = allFiles.map(file => sanitizeFiles(file.getName))
      allNodes <- combineStems(fileNodes ++ taggedNodes)
    } yield allNodes
  }

  def createNodePairs(allFiles: List[File]): List[FileAndTags] = {
    allFiles.map(file =>
      FileAndTags(sanitizeFiles(file.getName), findTaggedNodes(file))
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
      .find(_.getName == nameWithExtension) match {
      case Some(file) => {
        val bw = new BufferedWriter(new FileWriter(file))
        bw.write(body)
        bw.close()
      }
      case None => {
        val bw = new BufferedWriter(
          new FileWriter(
            "src/test/Resources/TestData/household/" + nameWithExtension
          )
        )
        bw.write(body)
        bw.close()
      }
    }
    createGraph
  }

  def deleteFile(fileName: String) = {
    val nameWithExtension = fileName + ".txt"
    LocalFileConsumer
      .getFiles("src/test/Resources/TestData", List(".txt"))
      .find(_.getName == nameWithExtension) match {
      case Some(file) => file.delete()
      case None => false
    }
  }
}
