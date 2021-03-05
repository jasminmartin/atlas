package NodeExposureSpec

import CommonModels.{Edge, Graph}
import NetExposure.RouteClient
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
              "dog.txt",
              "cat.txt",
              "sofa.txt",
              "chair.txt",
              "bathroom.txt",
              "[[sitting]]",
              "[[furniture]]",
              "[[furniture]]"
            ),
            List(
              Edge("sofa.txt", "[[sitting]]"),
              Edge("sofa.txt", "[[furniture]]"),
              Edge("chair.txt", "[[furniture]]")
            )
          )
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
