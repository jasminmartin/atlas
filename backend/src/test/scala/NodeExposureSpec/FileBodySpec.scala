package NodeExposureSpec

import CommonModels.{Edge, FileBody, Graph, NoFileFoundError}
import NetExposure.RouteClient
import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
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
    "/file-body/ is triggered" should {
      "return the /dog.txt body when called" in { f =>
        import f._

        Get(s"/file-body/dog.txt") ~> route ~> check {
          status shouldEqual StatusCodes.OK
          contentType shouldEqual ContentTypes.`application/json`
          val response = responseAs[FileBody]
          response shouldBe FileBody("dog.txt", "Dogs are animals. They are domesticated. They eat biscuits and drink water.")
        }
      }

      "return the /bathroom.txt body when called" in { f =>
        import f._

        Get(s"/file-body/bathroom.txt") ~> route ~> check {
          status shouldEqual StatusCodes.OK
          contentType shouldEqual ContentTypes.`application/json`
          val response = responseAs[FileBody]
          response shouldBe FileBody("bathroom.txt", "Contains a bath.")
        }
      }

      "throw an error when a non-existant file is called" in { f =>
        import f._
        Get(s"/file-body/nonexistant.txt") ~> route ~> check {
          status shouldEqual StatusCodes.BadRequest
          contentType shouldEqual ContentTypes.`application/json`
          val response = responseAs[NoFileFoundError]
          response shouldBe NoFileFoundError("pot")
        }
      }
    }
  }

  override protected def withFixture(test: OneArgTest): Outcome = {
    val zettelkastenRoute: RouteClient = RouteClient()

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
