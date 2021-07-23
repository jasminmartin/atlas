package LoadTest

import io.gatling.core.Predef.{scenario, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.{http, _}
import io.gatling.http.request.builder.HttpRequestBuilder

object Scenario {

  private val generateGraph: HttpRequestBuilder = http("Generate Graph")
    .get("/local-link")
    .check(status is 200)

  val generateGraphScenario: ScenarioBuilder =
    scenario("Generate Graph Scenario")
      .exec(generateGraph)
}
