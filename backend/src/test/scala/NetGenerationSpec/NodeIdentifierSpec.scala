package NetGenerationSpec

import java.io.File

import CommonModels._
import TagGeneration.NodeIdentifier
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.FixtureAnyWordSpec

class NodeIdentifierSpec extends FixtureAnyWordSpec with Matchers {

  markup {
    "NodeIdentifierSpec ensures the Node identifier scans documents for Nodes which are identified by '[[]]"
  }

  "NodeIdentifierSpec" when {
    "Given documents" should {
      "Identify nodes tagged in '[[]]'" in { f =>
        NodeIdentifier.findTaggedNodes(f.sofaFile) shouldEqual List(Node("[[sitting]]"), Node("[[furniture]]"))
      }

      "Identify all nodes (tagged and the file itself)" in { f =>
        NodeIdentifier.findAllFileNodes(List(f.sofaFile)) shouldEqual List(Node("sofa.txt"), Node("[[sitting]]"), Node("[[furniture]]"))
      }

      "Associate the tagged nodes '[[]]' to the file Node" in { f =>
        NodeIdentifier.createNodePairs(List(f.sofaFile)) shouldEqual
          List(FileAndNodes(Node("sofa.txt"), List(Node("[[sitting]]"), Node("[[furniture]]"))))
      }
    }
  }

  case class FixtureParam(sofaFile: File)

  override protected def withFixture(test: OneArgTest) = {
    val sofaFile = new File("src/test/Resources/household/sofa.txt")

    try {
      this.withFixture(test.toNoArgTest(
        FixtureParam(sofaFile)))
    }
  }
}
