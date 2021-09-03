package NetGenerationSpec

import CommonModels._
import FileIngestion.{FileConsumer, LocalFileConsumer}
import NetGeneration._
import org.scalatest.concurrent.Eventually.eventually
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.FixtureAnyWordSpec
import org.scalatest.{Assertion, Outcome}

import java.io.File

class NodeIdentifierSpec extends FixtureAnyWordSpec with Matchers {

  markup {
    "NodeIdentifierSpec ensures the Node identifier scans documents for Nodes which are identified by '[[]]"
  }

  "NodeIdentifierSpec" when {
    "Given documents" should {
      "Identify nodes tagged in '[[]]'" in { f =>
        NodeIdentifier
          .findTaggedNodes(f.sofaFile) shouldEqual List("sitting", "furniture")
      }

      "Identify all nodes (tagged and the file itself)" in { f =>
      val nodeIdentifier = NodeIdentifier
        nodeIdentifier.findAllFileNodes(List(f.sofaFile)) shouldEqual List(
          "sofa",
          "sitting",
          "furniture"
        )
      }

      "Associate the tagged nodes '[[]]' to the file Node" in { f =>
        val nodeIdentifier = NodeIdentifier
        nodeIdentifier.createNodePairs(List(f.sofaFile)) shouldEqual
            List(FileAndTags("sofa", List("sitting", "furniture")))
      }

      "Update nodes with new body information" in { f =>
        f.textCreator.updateFileBody(
          "sofa",
          "Sofas are for [[sitting]] on.\nThey are comfy 90% of the time.\nOr sometimes not.\nThey are a type of [[furniture]]. They are [[comfy]]"
        )
        val nodeIdentifier = NodeIdentifier
        nodeIdentifier.createNodePairs(List(f.sofaFile)) shouldEqual
          List(FileAndTags("sofa", List("sitting", "furniture", "comfy")))
        f.textCreator.updateFileBody(
          "sofa",
          "Sofas are for [[sitting]] on.\nThey are comfy 90% of the time.\nOr sometimes not.\nThey are a type of [[furniture]]."
        )
      }

      "Create new nodes" in { f =>
        val updatedGraph: Graph = f.textCreator.updateFileBody(
          "table",
          "Tables are often next to the [[sofa]].\nThey are [[furniture]]."
        )
        eventually {
          updatedGraph.edges.exists(x => x.firstNode == "table") shouldBe true
        }
      }
    }
  }

  case class FixtureParam(
      sofaFile: File,
      mockFileConsumer: FileConsumer,
      textCreator: GraphCreator
  )

  override protected def withFixture(test: OneArgTest): Outcome = {
    val sofaFile = new File("src/test/Resources/TestData/household/sofa.txt")
    val testSource = "src/test/Resources/TestData/household"
    val fileConsumer = LocalFileConsumer

    val textCreator = new GraphCreator(fileConsumer, testSource)
    try {
      this.withFixture(
        test.toNoArgTest(FixtureParam(sofaFile, fileConsumer, textCreator))
      )
    }
  }
}
