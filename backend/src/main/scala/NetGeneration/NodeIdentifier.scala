package NetGeneration

import java.io.File

import CommonModels.FileAndNodes

import scala.io.Source

object NodeIdentifier {

  private def stripNode(nodeName: String): String = {
    nodeName.replace("[[", "").replace("]]", "")
  }

  def findTaggedNodes(file: File): List[String] = {
    val tagRegex = """\[\[([^\[\]]+)\]\]""".r
    val bufferFile = Source.fromFile(file)
    val stringFile = bufferFile.mkString
    bufferFile.close()
    tagRegex.findAllMatchIn(stringFile).map(taggedNode => stripNode(taggedNode.toString)).toList
  }

//  def findAllFileNodes(allFiles: List[File]): List[String] = {
//    val taggedNodes: List[String] = allFiles.flatMap(file => findTaggedNodes(file))
//    val fileNodes: List[String] = allFiles.map(file => sanitizeFiles(file.getName))
//    val allNodes: List[String] = fileNodes ++ taggedNodes
//    allNodes
//  }
//
//  def createNodePairs(allFiles: List[File]): List[FileAndNodes] = {
//     allFiles.map(file => FileAndNodes(sanitizeFiles(file.getName), findTaggedNodes(file)))
//  }
}
