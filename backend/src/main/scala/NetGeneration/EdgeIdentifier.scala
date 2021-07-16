package NetGeneration

import CommonModels._

object EdgeIdentifier {

  def createEdges(fileAndTags: List[FileAndTags]): List[Edge] = {
    fileAndTags.flatMap(ft => (ft.tags)
      .map(stemmedTags => Edge((ft.file), stemmedTags)))
  }
}
