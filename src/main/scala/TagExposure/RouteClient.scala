package TagExposure

import java.io.File

import CommonModels.{FileTags, TagLink}
import FileIngestion.LocalFileConsumer
import TagGeneration.{TagIdentifier, TagLinker}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}

class RouteClient extends Directives {

  def route: Route =
    Route.seal(
      path( "link") {
        get {
          complete(run())
        }
      }
    )

  def run() = {
    val allFiles: Option[List[File]] = LocalFileConsumer.listFiles("src/test/Resources")
    val filteredFiles = LocalFileConsumer.filterFiles(allFiles.get, List(".txt"))
    val fileTagList = TagIdentifier.displayFileTags(filteredFiles)
     TagLinker.linkDocsByTags(fileTagList) match {
      //      case Right(c) => JsonParser.tagsToJson(tagList)
      case Right(c) => StatusCodes.OK
      case _ => StatusCodes.NotFound
    }
  }
}

object RouteClient {
  def apply(): RouteClient =
    new RouteClient
}

