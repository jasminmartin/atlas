//package FileIngestionSpec
//
//import io.circe.Json
//import io.circe.generic.auto._
//import io.circe.syntax._
//import CommonModels.NodePair
//import FileIngestion.LocalFileConsumer
//import akka.http.javadsl.server.Directives.route
//import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
//import akka.http.scaladsl.testkit.ScalatestRouteTest
//import org.scalatest.Outcome
//import org.scalatest.matchers.should.Matchers
//import org.scalatest.wordspec.FixtureAnyWordSpec
//
//class RouteSpec extends FixtureAnyWordSpec with Matchers with ScalatestRouteTest {
//
//  markup {
//    "RouteSpec checks that the Route returns the correct JSON"
//  }
//
//  "RouteSpec" when {
//    "/local-list is triggered" should {
//      "Return a list of the local nodes defined in /Resources" in { f =>
//        implicit def tagsToJson(tagLink: List[NodePair]): Json = {
//          tagLink.asJson
//        }
//        Get(s"/smartlist/current") ~> check {
//          status shouldEqual StatusCodes.OK
//          contentType shouldEqual ContentTypes.`application/json`
//        }
//    }
//  }
//
//
//  override protected def withFixture(test: OneArgTest): Outcome = {
//
//    val a = "hi"
//    super.withFixture(
//        test.toNoArgTest(
//          FixtureParam(a: String
//          )
//        )
//      )
//    }
//  }
//
//  case class FixtureParam(a: String)
//}
