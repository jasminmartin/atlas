package TagGenerationSpec

import CommonModels.{Edge, Node, NodePair}
import TagGeneration.EdgeIdentifier
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
          NodePair(Node("running.txt"), List(Node("trainers"), Node("protein"), Node("sprint"))),
          NodePair(Node("weightlifting.txt"), List(Node("trainers"), Node("protein"), Node("strength"))),
          NodePair(Node("swimming.txt"), List(Node("goggles"), Node("protein"), Node("water"))))

        EdgeIdentifier.singleEdge(linkedNodes) should contain(
          List(
            Edge(Node("running.txt"), "trainers"),
            Edge(Node("running.txt"), "protein"),
            Edge(Node("running.txt"), "sprint"),
            Edge(Node("weightlifting.txt"), "trainers"),
            Edge(Node("weightlifting.txt"), "protein"),
            Edge(Node("weightlifting.txt"), "strength"),
            Edge(Node("swimming.txt"), "goggles"),
            Edge(Node("swimming.txt"), "protein"),
            Edge(Node("swimming.txt"), "water")))
      }
    }
  }
}
