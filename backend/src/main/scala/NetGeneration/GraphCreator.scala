package NetGeneration

import CommonModels.{Edge, FileBody, Graph}
import FileIngestion.{FileParser, LocalFileParser}
import NetGeneration.Tokenizer.{createNodePairs, findAllFileNodes}

import java.io.{BufferedWriter, FileWriter}
import scala.io.Source

class GraphCreator(fileConsumer: FileParser, fileSource: String) {

  def createGraph: Graph = {
    val nodes: List[String] = findAllFileNodes(
      fileConsumer.getFiles(fileSource, List(".txt"))
    )
    println(s"all of the nodes ${nodes}")
    val edges: List[Edge] =
      EdgeIdentifier
        .createEdges(
          createNodePairs(
            LocalFileParser.getFiles(fileSource, List(".txt"))
          )
        )

    println(s"all of the edges ${edges}")

    Graph(nodes.distinct, edges)
  }


  def getFileBody(fileName: String): Option[FileBody] = {
    val nameWithExtension = fileName + ".txt"
    LocalFileParser
      .getFiles(fileSource, List(".txt"))
      .find(file => {
        file.getName == nameWithExtension
      })
      .map(file => {
        val bufferFile = Source.fromFile(file)
        val stringFile = bufferFile.mkString
        bufferFile.close()
        FileBody(fileName, stringFile.trim())
      })
  }

  def updateFileBody(fileName: String, body: String): Graph = {
    val nameWithExtension = fileName + ".txt"
    LocalFileParser
      .getFiles(fileSource, List(".txt"))
      .find(_.getName == nameWithExtension) match {
      case Some(file) => {
        val bw = new BufferedWriter(new FileWriter(file))
        bw.write(body)
        bw.close()
      }
      case None => {
        val bw = new BufferedWriter(
          new FileWriter(
            fileSource + nameWithExtension
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
    LocalFileParser
      .getFiles(fileSource, List(".txt"))
      .find(file => file.getName == nameWithExtension) match {
      case Some(file) => file.delete()
      case None => false
    }
  }
}
