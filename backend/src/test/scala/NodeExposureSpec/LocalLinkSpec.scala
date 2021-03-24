package NodeExposureSpec

import CommonModels.{Edge, Graph}
import FileIngestion.LocalFileConsumer
import NetExposure.RouteClient
import NetGeneration.TextGraphCreator
import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import org.scalatest.Outcome
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.FixtureAnyWordSpec

class LocalLinkSpec
    extends FixtureAnyWordSpec
    with ScalatestRouteTest
    with Matchers {

  markup {
    "Locallink checks that the Route returns the JSON net"
  }

  "RouteSpec" when {
    "/local-list is triggered" should {
      "Return a list of the local nodes defined in /Resources" in { f =>
        import f._

        Get(s"/local-link") ~> route ~> check {
          status shouldEqual StatusCodes.OK
          contentType shouldEqual ContentTypes.`application/json`
          val response = responseAs[Graph]
          response shouldBe Graph(
            List(
              "dog",
              "cat",
              "sofa",
              "chair",
              "bathroom",
              "sitting",
              "furniture",
              "furniture"
            ),
            List(
              Edge("sofa", "sitting"),
              Edge("sofa", "furniture"),
              Edge("chair", "furniture")
            )
          )
        }
      }
    }
  }

  override protected def withFixture(test: OneArgTest): Outcome = {
    val localFileConsumer = LocalFileConsumer
    val textGraphCreator = new TextGraphCreator(localFileConsumer, "src/test/Resources")
    val zettelkastenRoute: RouteClient = new RouteClient(textGraphCreator)

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
