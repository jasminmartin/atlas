package NetGeneration

import CommonModels._

object EdgeIdentifier {

  def singleEdge(fileAndTags: List[FileAndNodes]): List[Edge] = {
    val distinctEdges = fileAndTags.flatten(ft => ft.tags.map(tag => Edge(ft.file, tag)))
    distinctEdges
  }
}
