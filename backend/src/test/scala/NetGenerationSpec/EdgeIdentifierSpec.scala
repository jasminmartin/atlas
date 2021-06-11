package NetGenerationSpec
import CommonModels._
import NetGeneration.EdgeIdentifier
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class EdgeIdentifierSpec extends AnyWordSpec with Matchers {

  markup {
    "the EdgeIdentifierSpec links related nodes"
  }

  "EdgeIdentifierSpec" when {

    "Given a list of nodes" should {
      "Link nodes which are related" in {
        val linkedNodes = List(
          FileAndTags(
            "running.txt",
            List("trainer", "protein", "sprint")
          ),
          FileAndTags(
            "weightlifting.txt",
            List("trainer", "protein", "strength")
          ),
          FileAndTags(
            "swimming.txt",
            List("goggl", "protein", "water")
          )
        )

        EdgeIdentifier.createEdges(linkedNodes) shouldEqual List(
            Edge("running.txt", "trainer"),
            Edge("running.txt", "protein"),
            Edge("running.txt", "sprint"),
            Edge("weightlifting.txt", "trainer"),
            Edge("weightlifting.txt", "protein"),
            Edge("weightlifting.txt", "strength"),
            Edge("swimming.txt", "goggl"),
            Edge("swimming.txt", "protein"),
            Edge("swimming.txt", "water")
          )
      }
    }
  }
}
