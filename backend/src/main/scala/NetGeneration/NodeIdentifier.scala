package NetGeneration

import java.io.File
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
    tagRegex
      .findAllMatchIn(stringFile)
      .map(taggedNode => stripNode(taggedNode.toString))
      .toList
  }

  def combineStems(filesAndNodes: List[String]): List[String] = {
    val d= filesAndNodes.map(name => removeSuffix(name)).distinct
    println(s"combineStems + $d")
    d
  }

  def removeSuffix(str: String): String = {
    str match {
      case str if str.endsWith("ing") => str.dropRight(3)
      case str if str.endsWith("ed") => str.dropRight(2)
      case str if str.endsWith("es") => str.dropRight(2)
      case str => str
    }
  }
}
