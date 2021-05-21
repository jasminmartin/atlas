package NetGeneration

import CommonModels._

object EdgeIdentifier {

  def createEdges(fileAndTags: List[FileAndNodes]): List[Edge] = {
    val c = fileAndTags.flatten(ft => ft.tags.map(tag => Edge(ft.file, tag)))
    println(s"createEdges + $c")
    c
  }
}
