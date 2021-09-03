package NodeExposureSpec

import CommonModels.{Edge, FileBody, Graph}
import FileIngestion.LocalFileConsumer
import NetExposure.RouteClient
import NetGeneration.GraphCreator
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.Json
import io.circe.generic.auto._
import io.circe.syntax.EncoderOps
import org.scalatest.Outcome
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.FixtureAnyWordSpec

class FileBodySpec
    extends FixtureAnyWordSpec
    with ScalatestRouteTest
    with Matchers {

  markup {
    "FileBodySpec checks that the Route returns the selected file body"
  }

  "RouteSpec" when {
    "GET /file-body/ is triggered" should {
      "return the /dog body when called" in { f =>
        import f._

        Get(s"/file-body/dog") ~> route ~> check {
          status shouldEqual StatusCodes.OK
          contentType shouldEqual ContentTypes.`application/json`
          val response = responseAs[FileBody]
          response shouldBe FileBody(
            "dog",
            "Dogs are animals. They are domesticated. They eat biscuits and drink water."
          )
        }
      }

      "return the /bathroom body when called" in { f =>
        import f._

        Get(s"/file-body/bathroom") ~> route ~> check {
          status shouldEqual StatusCodes.OK
          contentType shouldEqual ContentTypes.`application/json`
          val response = responseAs[FileBody]
          response shouldBe FileBody("bathroom", "Contains a bath.")
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
        val requestEntity: Json =
          FileBody("bathroom", "Is for [[sitting]] in.").asJson

        val entity =
          HttpEntity(ContentTypes.`application/json`, requestEntity.toString())

        Post(s"/file-body/bathroom", entity) ~> f.route ~> check {
          contentType shouldEqual ContentTypes.`application/json`
          status shouldEqual StatusCodes.OK
        }

        Get(s"/file-body/bathroom") ~> f.route ~> check {
          status shouldEqual StatusCodes.OK
          contentType shouldEqual ContentTypes.`application/json`
          val response = responseAs[FileBody]
          response shouldBe FileBody("bathroom", "Is for [[sitting]] in.")
        }

        Get(s"/local-link") ~> f.route ~> check {
          status shouldEqual StatusCodes.OK
          contentType shouldEqual ContentTypes.`application/json`
          val response = responseAs[Graph]
          response.nodes should contain allOf ("Animals",
          "sofa",
          "chair",
          "lion",
          "bathroom",
          "cat",
          "dog",
          "furniture",
          "sitting")
          response.edges should contain allOf
            (
              Edge("Animals", "cat"),
              Edge("Animals", "dog"),
              Edge("sofa", "sitting"),
              Edge("sofa", "furniture"),
              Edge("chair", "furniture"),
              Edge("lion", "cat"),
              Edge("bathroom","sitting")

          )
        }

        val revert: Json =
          FileBody("bathroom", "Contains a bath.").asJson

        Post(s"/file-body/bathroom", revert) ~> f.route ~> check {
          contentType shouldEqual ContentTypes.`application/json`
          status shouldEqual StatusCodes.OK
        }
      }
    }
    "Delete /file-body/ is triggered " should {
      "Delete the bathroom.txt file" in { f =>

        Get(s"/file-body/bathroom") ~> f.route ~> check {
          status shouldEqual StatusCodes.OK
          contentType shouldEqual ContentTypes.`application/json`
          val response = responseAs[FileBody]
          response shouldBe FileBody("bathroom", "Contains a bath.")
        }

        Delete(s"/file-body/bathroom") ~> f.route ~> check {
          status shouldEqual StatusCodes.NoContent
        }

        val revert: Json =
          FileBody("bathroom", "Contains a bath.").asJson

        Post(s"/file-body/bathroom", revert) ~> f.route ~> check {
          contentType shouldEqual ContentTypes.`application/json`
          status shouldEqual StatusCodes.OK
        }

        Get(s"/file-body/bathroom") ~> f.route ~> check {
          status shouldEqual StatusCodes.OK
          contentType shouldEqual ContentTypes.`application/json`
          val response = responseAs[FileBody]
          response shouldBe FileBody("bathroom", "Contains a bath.")
        }
      }

      "Respond with a Bad request if a file cannot be found to delete" in { f =>

        Delete(s"/file-body/notarealfile") ~> f.route ~> check {
          status shouldEqual StatusCodes.NotFound
        }
      }
    }
  }

  override protected def withFixture(test: OneArgTest): Outcome = {
    val localFileConsumer = LocalFileConsumer
    val GraphCreator =
      new GraphCreator(localFileConsumer, "src/test/Resources/TestData")
    val zettelkastenRoute: RouteClient = new RouteClient(GraphCreator)

    super.withFixture(
      test.toNoArgTest(
        FixtureParam(zettelkastenRoute)
      )
    )
  }

  case class FixtureParam(zettelkastenRoute: RouteClient) {
    val route: Route = Route.seal(zettelkastenRoute.route)
  }
}
