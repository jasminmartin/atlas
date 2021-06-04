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
    filesAndNodes.map(name => removeSuffix(name)).distinct
  }

  def removeSuffix(str: String): String = {
    str match {
      case str if str.endsWith("es") => str.dropRight(2)
      case str if str.endsWith("ed")
        && str(str.length -1) !=("a")
        && str(str.length -1) !=("e")
        && str(str.length -1) !=("i")
        && str(str.length -1) !=("o")
        && str(str.length -1) !=("u")
      => str.dropRight(2)
      case str if str.endsWith("s") && str(str.length -2) !='s' => str.dropRight(1)
      case str => str
    }
  }
}
