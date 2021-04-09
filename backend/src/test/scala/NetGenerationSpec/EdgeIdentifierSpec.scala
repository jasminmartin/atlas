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
          FileAndNodes(
            "running.txt",
            List("trainers", "protein", "sprint")
          ),
          FileAndNodes(
            "weightlifting.txt",
            List("trainers", "protein", "strength")
          ),
          FileAndNodes(
            "swimming.txt",
            List("goggles", "protein", "water")
          )
        )

        EdgeIdentifier.createEdges(linkedNodes) shouldEqual List(
            Edge("running.txt", "trainers"),
            Edge("running.txt", "protein"),
            Edge("running.txt", "sprint"),
            Edge("weightlifting.txt", "trainers"),
            Edge("weightlifting.txt", "protein"),
            Edge("weightlifting.txt", "strength"),
            Edge("swimming.txt", "goggles"),
            Edge("swimming.txt", "protein"),
            Edge("swimming.txt", "water")
          )
      }
    }
  }
}
