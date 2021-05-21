package NetGenerationSpec

import java.io.File
import CommonModels._
import FileIngestion.FileConsumer
import NetGeneration._
import org.scalatest.Outcome
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.FixtureAnyWordSpec
import org.mockito.MockitoSugar.mock

class NodeIdentifierSpec extends FixtureAnyWordSpec with Matchers {

  markup {
    "NodeIdentifierSpec ensures the Node identifier scans documents for Nodes which are identified by '[[]]"
  }

  "NodeIdentifierSpec" when {
    "Given documents" should {
      "Identify nodes tagged in '[[]]'" in { f =>
        NodeIdentifier.findTaggedNodes(f.sofaFile) shouldEqual List("sitting", "furniture")
      }

      "Identify all nodes (tagged and the file itself)" in { f =>
        f.textCreator.findAllFileNodes(List(f.sofaFile)) shouldEqual List("sofa", "sitting", "furniture")
      }

      "Associate the tagged nodes '[[]]' to the file Node" in { f =>
        f.textCreator.createNodePairs(List(f.sofaFile)) shouldEqual
          List(FileAndNodes("sofa", List("sitting", "furniture")))
      }

      "Combine empty files with the same stem" in { f => {pending}}
    }
  }

  case class FixtureParam(sofaFile: File, mockFileConsumer: FileConsumer, textCreator: TextGraphCreator)

  override protected def withFixture(test: OneArgTest): Outcome = {
    val sofaFile = new File("src/test/Resources/TestData/household/sofa.txt")
    val testSource = "src/test/Resources/TestData/household"
    val mockFileConsumer = mock[FileConsumer]
    val textCreator = new TextGraphCreator(mockFileConsumer, testSource)
    try {
      this.withFixture(test.toNoArgTest(
        FixtureParam(sofaFile, mockFileConsumer, textCreator)))
    }
  }
}
