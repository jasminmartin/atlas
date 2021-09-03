import java.io.File
import CommonModels.{Edge, FileAndTags}
import FileIngestion.{FileConsumer, LocalFileConsumer}
import NetGeneration.{EdgeIdentifier, NodeIdentifier, GraphCreator}
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
        val nodeIdentifier = NodeIdentifier

        val fileTagList: Seq[String] =
          nodeIdentifier.findAllFileNodes(filteredFiles)
        fileTagList should contain theSameElementsAs
          List(
            "Animals",
            "table",
            "sofa",
            "chair",
            "dog",
            "cat",
            "lion",
            "bathroom",
            "Animals",
            "table",
            "sofa",
            "chair",
            "dog",
            "cat",
            "lion",
            "bathroom",
            "furniture",
            "Animals",
            "table",
            "sofa",
            "chair",
            "dog",
            "cat",
            "lion",
            "bathroom",
            "sitting",
            "furniture",
            "Animals",
            "table",
            "sofa",
            "chair",
            "dog",
            "cat",
            "lion",
            "bathroom",
            "furniture",
            "Animals",
            "table",
            "sofa",
            "chair",
            "dog",
            "cat",
            "lion",
            "bathroom",
            "Animals",
            "table",
            "sofa",
            "chair",
            "dog",
            "cat",
            "lion",
            "bathroom",
            "Animals",
            "table",
            "sofa",
            "chair",
            "dog",
            "cat",
            "lion",
            "bathroom",
            "Animals",
            "table",
            "sofa",
            "chair",
            "dog",
            "cat",
            "lion",
            "bathroom"
          )
      }
    }
  }

  case class FixtureParam(
      nestedDirectoryStructure: String,
      textCreator: GraphCreator
  )

  override protected def withFixture(test: OneArgTest): Outcome = {
    val nestedDirectoryStructure: String = "src/test/Resources/TestData"
    val testSource = "src/test/Resources/TestData/household"
    val mockFileConsumer = mock[FileConsumer]
    val textCreator = new GraphCreator(mockFileConsumer, testSource)

    try {
      this.withFixture(
        test.toNoArgTest(
          FixtureParam(
            nestedDirectoryStructure: String,
            textCreator: GraphCreator
          )
        )
      )
    }
  }
}
