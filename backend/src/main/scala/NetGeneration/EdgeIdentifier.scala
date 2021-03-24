package NetGeneration

import CommonModels._

object EdgeIdentifier {

  def createEdges(fileAndTags: List[FileAndNodes]): List[Edge] = {
    fileAndTags.flatten(ft => ft.tags.map(tag => Edge(ft.file, tag)))
  }
}
