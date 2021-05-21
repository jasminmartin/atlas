import java.io.File
import CommonModels.{Edge, FileAndNodes}
import FileIngestion.{FileConsumer, LocalFileConsumer}
import NetGeneration.{EdgeIdentifier, NodeIdentifier, TextGraphCreator}
import org.mockito.MockitoSugar.mock
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
          f.textCreator.findAllFileNodes(filteredFiles)
        fileTagList should contain theSameElementsAs
          List(
            "Animals",
            "sofa",
            "chair",
            "dog",
            "cat",
            "lion",
            "bathroom",
            "cat",
            "dog",
            "sitting",
            "furniture",
            "furniture",
            "cat"
          )
      }
    }
  }

  case class FixtureParam(nestedDirectoryStructure: String, textCreator: TextGraphCreator)

  override protected def withFixture(test: OneArgTest): Outcome = {
    val nestedDirectoryStructure: String = "src/test/Resources/TestData"
    val testSource = "src/test/Resources/TestData/household"
    val mockFileConsumer = mock[FileConsumer]
    val textCreator = new TextGraphCreator(mockFileConsumer, testSource)

    try {
      this.withFixture(
        test.toNoArgTest(FixtureParam(nestedDirectoryStructure: String, textCreator: TextGraphCreator))
      )
    }
  }
}
