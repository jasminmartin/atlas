import java.io.File

import CommonModels.{Edge, Node, FileAndNodes}
import FileIngestion.LocalFileConsumer
import TagGeneration.{EdgeIdentifier, NodeIdentifier}
import org.scalatest.Outcome
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.FixtureAnyWordSpec

class FunctionalTestSpec extends FixtureAnyWordSpec with Matchers {

  markup {
    "FunctionalTestSpec tests the functionality of the app as whole"
  }

  "FunctionalTestSpec" when {
    "Given a directory" should {
      "Return a list of nodes" in { f =>
        val allFiles: Option[List[File]] = LocalFileConsumer.listFiles(f.nestedDirectoryStructure)
        val filteredFiles: List[File] = LocalFileConsumer.filterFiles(allFiles.get, List(".txt"))
        val fileTagList: List[Node] = NodeIdentifier.findAllFileNodes(filteredFiles)
        fileTagList should contain theSameElementsAs
          List(Node("dog.txt"), Node("cat.txt"), Node("sofa.txt"), Node("chair.txt"), Node("bathroom.txt"), Node("[[sitting]]"), Node("[[furniture]]"), Node("[[furniture]]"))
      }

      "Link nodes" in { f =>
        val allFiles: Option[List[File]] = LocalFileConsumer.listFiles(f.nestedDirectoryStructure)
        val filteredFiles: List[File] = LocalFileConsumer.filterFiles(allFiles.get, List(".txt"))
        val fileTagList: List[FileAndNodes] = NodeIdentifier.createNodePairs(filteredFiles)
        EdgeIdentifier.singleEdge(fileTagList) shouldEqual List(Edge(Node("sofa.txt"),"[[sitting]]"), Edge(Node("sofa.txt"),"[[furniture]]"), Edge(Node("chair.txt"),"[[furniture]]"))
      }
    }
  }

  case class FixtureParam(nestedDirectoryStructure: String)

  override protected def withFixture(test: OneArgTest): Outcome = {
    val nestedDirectoryStructure: String = "src/test/Resources"

    try {
      this.withFixture(test.toNoArgTest(FixtureParam(nestedDirectoryStructure: String)))
    }
  }
}

