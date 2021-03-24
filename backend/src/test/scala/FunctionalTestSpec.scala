import java.io.File

import CommonModels.{Edge, FileAndNodes}
import FileIngestion.LocalFileConsumer
import NetGeneration.{EdgeIdentifier, NodeIdentifier}
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
        val allFiles: Option[List[File]] =
          LocalFileConsumer.isDirectory(f.nestedDirectoryStructure)
        val filteredFiles: List[File] =
          LocalFileConsumer.filterFileExtensions(allFiles.get, List(".txt"))
        val fileTagList: Seq[String] =
          NodeIdentifier.findAllFileNodes(filteredFiles)
        fileTagList should contain theSameElementsAs
          List(
            "dog",
            "cat",
            "sofa",
            "chair",
            "bathroom",
            "sitting",
            "furniture",
            "furniture"
          )
      }
    }
  }

  case class FixtureParam(nestedDirectoryStructure: String)

  override protected def withFixture(test: OneArgTest): Outcome = {
    val nestedDirectoryStructure: String = "src/test/Resources"

    try {
      this.withFixture(
        test.toNoArgTest(FixtureParam(nestedDirectoryStructure: String))
      )
    }
  }
}
