package NetGeneration

import CommonModels.FileAndTags

import java.io.File
import scala.io.Source

object NodeIdentifier {


  def findTaggedNodes(file: File): List[String] = {
    val tagRegex = """\[\[([^\[\]]+)\]\]""".r
    val bufferFile = Source.fromFile(file)
    val stringFile = bufferFile.mkString
    bufferFile.close()
    tagRegex
      .findAllMatchIn(stringFile)
      .map(taggedNode => stripNode(taggedNode.toString))
      .toList
  }


  def findAllFileNodes(allFiles: List[File]): List[String] = {
    for {
    taggedNodes <- allFiles.map(file => findTaggedNodes(file))
    fileNodes = allFiles.map(file => sanitizeFiles(file.getName))
    allNodes <- fileNodes ++ taggedNodes
    } yield allNodes
  }

  def createNodePairs(allFiles: List[File]): List[FileAndTags] = {
    allFiles.map(file =>
      FileAndTags(sanitizeFiles(file.getName), findTaggedNodes(file)))
  }

  private def stripNode(nodeName: String): String = {
    nodeName.replace("[[", "").replace("]]", "")
  }

  private def sanitizeFiles(fileNames: String): String = {
    fileNames.replace(".txt", "")
  }
}
