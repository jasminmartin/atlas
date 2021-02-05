package TagGeneration

import CommonModels._

object EdgeIdentifier {

  def singleEdge(fileAndTags: List[NodePair]): List[Edge] = {
    val distinctEdges = fileAndTags.flatten(ft => ft.tags.map(tag => Edge(ft.file, tag.tag))).distinct
    println(distinctEdges)
    distinctEdges
  }
}
