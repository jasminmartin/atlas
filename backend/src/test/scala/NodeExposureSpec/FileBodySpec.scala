package NodeExposureSpec

import CommonModels.FileBody
import FileIngestion.LocalFileParser
import NetExposure.RouteClient
import NetGeneration.GraphCreator
import Utils.AtlasFileUtil
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import io.circe.syntax.EncoderOps
import org.scalatest.concurrent.Eventually.eventually
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.FixtureAnyWordSpec
import org.scalatest.{BeforeAndAfterEach, Outcome}

class FileBodySpec
    extends FixtureAnyWordSpec
    with ScalatestRouteTest
    with Matchers
    with BeforeAndAfterEach {

  markup {
    "FileBodySpec checks that the Route returns the selected file body"
  }

  val utils = new AtlasFileUtil

  override def beforeEach {
    utils.allFileContents.map(pair =>
      utils.createFileStructure(pair._1, pair._2)
    )
    Thread.sleep(2000)
  }

  override def afterEach {
    utils.deleteFilesInTestinDir
    Thread.sleep(1000)
  }

  "FileBodySpec" when {
    "GET /file-body/ is triggered" should {
      "return the /cat body when called" in { f =>
        import f._

        Get(s"/file-body/${utils.cat}") ~> route ~> check {
          status shouldEqual StatusCodes.OK
          contentType shouldEqual ContentTypes.`application/json`
          val response = responseAs[FileBody]
          response shouldBe FileBody(
            utils.cat,
            utils.catContent
          )
        }
      }

      "return the /dog body when called" in { f =>
        import f._

        Get(s"/file-body/${utils.dog}") ~> route ~> check {
          status shouldEqual StatusCodes.OK
          contentType shouldEqual ContentTypes.`application/json`
          val response = responseAs[FileBody]
          response shouldBe FileBody(
            utils.dog,
            utils.dogContent
          )
        }
      }

      "throw an error when a non-existent file is called" in { f =>
        import f._
        Get(s"/file-body/nonexistent") ~> route ~> check {
          status shouldEqual StatusCodes.NotFound
        }
      }
    }

    "Post /file-body/ is triggered" should {
      "update a file-body with the new put information" in { f =>
        val entity =
          HttpEntity(
            ContentTypes.`application/json`,
            FileBody(utils.bathroom, "[[new-tag]]").asJson.toString()
          )

        Post(s"/file-body/${utils.bathroom}", entity) ~> f.route ~> check {
          contentType shouldEqual ContentTypes.`application/json`
          status shouldEqual StatusCodes.OK
        }

        Get(s"/file-body/${utils.bathroom}") ~> f.route ~> check {
          eventually {
            status shouldEqual StatusCodes.OK
            contentType shouldEqual ContentTypes.`application/json`
            val response = responseAs[FileBody]
            response shouldBe FileBody(utils.bathroom, "[[new-tag]]")
          }
        }
      }
    }

    "Delete /file-body/ is triggered " should {
      "Delete the bathroom.txt file" in { f =>
        Get(s"/file-body/${utils.lion}") ~> f.route ~> check {
          status shouldEqual StatusCodes.OK
          contentType shouldEqual ContentTypes.`application/json`
          val response = responseAs[FileBody]
          response shouldBe FileBody(utils.lion, utils.lionContent)
        }

        Delete(s"/file-body/${utils.lion}") ~> f.route ~> check {
          status shouldEqual StatusCodes.NoContent
        }
      }

      "Respond with a Bad request if a file cannot be found to delete" in { f =>
        Delete(s"/file-body/nonexistent") ~> f.route ~> check {
          status shouldEqual StatusCodes.NotFound
        }
      }
    }
  }

  override protected def withFixture(test: OneArgTest): Outcome = {
    val localFileConsumer = LocalFileParser
    val GraphCreator =
      new GraphCreator(localFileConsumer, utils.testingDirectory)
    val atlasRoute: RouteClient = new RouteClient(GraphCreator)

    super.withFixture(
      test.toNoArgTest(
        FixtureParam(atlasRoute)
      )
    )
  }

  case class FixtureParam(altasRouteClient: RouteClient) {
    val route: Route = Route.seal(altasRouteClient.route)
  }
}
