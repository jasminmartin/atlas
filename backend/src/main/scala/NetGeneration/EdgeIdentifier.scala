package NetGeneration

import CommonModels._
import NetGeneration.Stemmer.{combineStems, removeSuffix}

object EdgeIdentifier {

  def createEdges(fileAndTags: List[FileAndTags]): List[Edge] = {
    fileAndTags.flatMap(ft => combineStems(ft.tags)
      .map(stemmedTags => Edge(removeSuffix(ft.file), stemmedTags)))
  }
}
