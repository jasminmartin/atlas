package TagExposure

import java.io.File

import CommonModels.{FileTags, TagLink}
import FileIngestion.LocalFileConsumer
import TagGeneration.{TagIdentifier, TagLinker}
import akka.http.scaladsl.server.{Directives, Route}

private class Routes extends Directives {

   def route: Route =
    Route.seal(
      path("zettelkasten" / "link" / Segment) { directoryPath =>
        get {
          concat(run(directoryPath))
        }
      }
    )

  def run(directoryPath: String) = {
    val allFiles: Option[List[File]] = LocalFileConsumer.listFiles(directoryPath)
    val filteredFiles = LocalFileConsumer.filterFiles(allFiles.get, List(".txt"))
    val fileTagList = TagIdentifier.displayFileTags(filteredFiles)
    val tagList = TagLinker.linkDocsByTags(fileTagList) match {
      case Right(c) => JsonParser.tagsToJson(tagList)
      case _ => "boo"
    }
  }
}

