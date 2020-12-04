package TagExposure

import java.io.File

import CommonModels.{FileTags, TagLink}
import FileIngestion.LocalFileConsumer
import TagGeneration.{TagIdentifier, TagLinker}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}

import scala.util.Success

class RouteClient extends Directives {
  import Directives._
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  def route: Route =
    Route.seal(
      path("link") {
        TagLinker.linkDocsByTags(getLocalTags()) match {
          case Right(c) => complete(c)
          case Left(e)  => complete(StatusCodes.NotFound -> e)
        }
      }
    )

  def getLocalTags() = {
    val allFiles: Option[List[File]] =
      LocalFileConsumer.listFiles("src/test/Resources")
    val filteredFiles =
      LocalFileConsumer.filterFiles(allFiles.get, List(".txt"))
    TagIdentifier.displayFileTags(filteredFiles)
  }
}

object RouteClient {
  def apply(): RouteClient =
    new RouteClient
}
