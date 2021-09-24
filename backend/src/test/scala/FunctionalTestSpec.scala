import CommonModels.FileBody
import FileIngestion.LocalFileConsumer
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

class FunctionalTestSpec extends FixtureAnyWordSpec with Matchers with ScalatestRouteTest
  with BeforeAndAfterEach {

  markup {
    "FunctionalTestSpec tests Atlas user journeys"
  }

  val utils = new AtlasFileUtil

  override def beforeEach {
    utils.allFileContents.map(pair => utils.createFileStructure(pair._1, pair._2))
    Thread.sleep(1000)
  }

  override def afterEach {
    utils.deleteFilesInTestinDir
    Thread.sleep(1000)
  }

  "FunctionalTestSpec" when {
    "A user creates a new Atlas note" should {
      "Update the Atlas web" in { f =>
        Get(s"/file-body/${utils.dog}") ~> f.route ~> check {
          status shouldEqual StatusCodes.OK
          contentType shouldEqual ContentTypes.`application/json`
          val response = responseAs[FileBody]
          response shouldBe FileBody(
            utils.dog,
            utils.dogContent
          )
        }
        val entity =
          HttpEntity(ContentTypes.`application/json`, FileBody(utils.cat, "[[new-tag]]").asJson.toString())
        Post(s"/file-body/${utils.cat}", entity) ~> f.route ~> check {
            status shouldEqual StatusCodes.OK
        }
        Thread.sleep(1000)
        Get(s"/file-body/${utils.cat}") ~> f.route ~> check {
          status shouldEqual StatusCodes.OK
          contentType shouldEqual ContentTypes.`application/json`
          val response = responseAs[FileBody]
          eventually {
            response shouldBe FileBody(utils.cat, "[[new-tag]]")
          }
        }

        Delete(s"/file-body/${utils.cat}") ~> f.route ~> check {
          status shouldEqual StatusCodes.NoContent
        }

        Get(s"/file-body/${utils.cat}") ~> f.route ~> check {
          status shouldEqual StatusCodes.NotFound
        }
      }
    }
  }

  override protected def withFixture(test: OneArgTest): Outcome = {
    val GraphCreator =
      new GraphCreator(LocalFileConsumer, utils.testingDirectory)
    val altasRoute: RouteClient = new RouteClient(GraphCreator)

    super.withFixture(
      test.toNoArgTest(
        FixtureParam(altasRoute)
      )
    )
  }

  case class FixtureParam(altasRoute: RouteClient) {
    val route: Route = Route.seal(altasRoute.route)
  }
}
