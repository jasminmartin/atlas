//package NodeExposureSpec
//
//class LocalLinkSpec
//    extends FixtureAnyWordSpec
//    with ScalatestRouteTest
//    with Matchers {
//
//  markup {
//    "Locallink checks that the Route returns the JSON net"
//  }
//
//  "RouteSpec" when {
//    "/local-list is triggered" should {
//      "Return a list of the local nodes defined in /Resources/TestData" in {
//        f =>
//          Get(s"/local-link") ~> route ~> check {
//            status shouldEqual StatusCodes.OK
//            contentType shouldEqual ContentTypes.`application/json`
//            val response = responseAs[Graph]
//            response shouldBe Graph(
//              List(
//                "Animals",
//                "sofa",
//                "chair",
//                "dog",
//                "cat",
//                "lion",
//                "bathroom",
//                "cat",
//                "dog",
//                "sitting",
//                "furniture",
//                "furniture",
//                "cat"
//              ),
//              List(
//                Edge("sofa", "sitting"),
//                Edge("sofa", "furniture"),
//                Edge("chair", "furniture"),
//                Edge("cat", "lion")
//              )
//            )
//          }
//      }
//    }
//  }
//
//  override protected def withFixture(test: OneArgTest): Outcome = {
//    val localFileConsumer = LocalFileConsumer
//    val textGraphCreator =
//      new TextGraphCreator(localFileConsumer, "src/test/Resources/TestData")
//    val zettelkastenRoute: RouteClient = new RouteClient(textGraphCreator)
//
//    super.withFixture(
//      test.toNoArgTest(
//        FixtureParam(zettelkastenRoute)
//      )
//    )
//  }
//
//  case class FixtureParam(zettelkastenRoute: RouteClient) {
//    val route: Route = Route.seal(zettelkastenRoute.route)
//  }
//}
