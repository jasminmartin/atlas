package NodeExposureSpec

import CommonModels.{Edge, Graph}
import FileIngestion.{LocalFileParser}
import NetExposure.RouteClient
import NetGeneration.GraphCreator
import Utils.AtlasFileUtil
import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.FixtureAnyWordSpec
import org.scalatest.{BeforeAndAfter, BeforeAndAfterEach, Outcome}

class LocalLinkSpec
    extends FixtureAnyWordSpec
    with ScalatestRouteTest
    with Matchers
    with BeforeAndAfterEach {

  markup {
    "LocalLinkSpeck checks that the Route returns the Atlas Web Json"
  }

  val utils = new AtlasFileUtil

  override def beforeEach {
    utils.allFileContents.map(pair =>
      utils.createFileStructure(pair._1, pair._2)
    )
  }

  override def afterEach {
    utils.deleteFilesInTestinDir
  }

  "LocalLinkSpec" when {
    "/local-list is triggered" should {

      "Return a list of the local nodes" in { f =>
        Get(s"/local-link") ~> f.route ~> check {
          status shouldEqual StatusCodes.OK
          contentType shouldEqual ContentTypes.`application/json`
          val response = responseAs[Graph]
          response.nodes should contain allElementsOf utils.allWords
          response.edges should contain allElementsOf utils.allWords
            .map(word => Edge(word, (word)))
            .toList
        }
      }
    }
  }

  override protected def withFixture(test: OneArgTest): Outcome = {
    val GraphCreator =
      new GraphCreator(LocalFileParser, utils.testingDirectory)
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
