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
}
