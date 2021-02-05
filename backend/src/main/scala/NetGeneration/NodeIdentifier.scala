package TagGeneration

import java.io.File

import CommonModels.{Node, NodePair}

import scala.io.Source

object NodeIdentifier {

  def findTaggedNodes(file: File): List[Node] = {
    val tagRegex = """\[\[([^\[\]]+)\]\]""".r
    val bufferFile = Source.fromFile(file)
    val stringFile = bufferFile.mkString
    bufferFile.close()
    tagRegex.findAllMatchIn(stringFile).map(taggedNode => Node(taggedNode.toString)).toList
  }

  def findAllFileNodes(allFiles: List[File]): List[Node] = {
    val taggedNodes: List[Node] = allFiles.flatMap(file => findTaggedNodes(file))
    val fileNodes: List[Node] = allFiles.map(file => Node(file.getName))
    val allNodes: List[Node] = fileNodes ++ taggedNodes
    allNodes
  }

  def createNodePairs(allFiles: List[File]): List[NodePair] = {
     allFiles.map(file => NodePair(Node(file.getName), findTaggedNodes(file)))
  }
}
