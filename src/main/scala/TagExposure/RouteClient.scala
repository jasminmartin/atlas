package TagExposure

import java.io.File

import CommonModels.FileAndTags
import FileIngestion.LocalFileConsumer
import TagGeneration.{TagIdentifier, TagLinker}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}

class RouteClient extends Directives {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  def route: Route =
    Route.seal(
      path("local-link") {
        TagLinker.linkDocsByTags(getLocalTags) match {
          case Right(taggedFiles) => complete(taggedFiles)
          case Left(e) => complete(StatusCodes.NotFound -> e)
        }
      }
    )

  def getLocalTags: List[FileAndTags] = {
    val allLocalFiles: Option[List[File]] = LocalFileConsumer.listFiles("src/test/Resources")
    val filterFiles = LocalFileConsumer.filterFiles(allLocalFiles.get, List(".txt"))
    TagIdentifier.fileAndTags(filterFiles)
  }
}

object RouteClient {
  def apply(): RouteClient =
    new RouteClient
}
