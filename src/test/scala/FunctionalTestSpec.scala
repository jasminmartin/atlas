import java.io.File

import org.scalatest.Outcome
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.FixtureAnyWordSpec

class FunctionalTestSpec extends FixtureAnyWordSpec with Matchers {

  markup {
    "FunctionalTestSpec tests the functionality of the app as whole"
  }

  "FunctionalTestSpec" when {
    "Given a directory" should {
      "Return a list of documents and related tags" in { f =>
        val allFiles: Option[List[File]] = LocalFileConsumer.listFiles(f.nestedDirectoryStructure)
        val filteredFiles: List[File] = LocalFileConsumer.filterFiles(allFiles.get, List(".txt"))
        val fileTagList = TagIdentifier.displayFileTags(filteredFiles)
        fileTagList should contain theSameElementsAs
          List(Map(FileMetaData("dog.txt") -> List()), Map(FileMetaData("cat.txt") -> List()), Map(FileMetaData("sofa.txt") -> List(Tag("[[sitting]]"), Tag("[[furniture]]"))), Map(FileMetaData("chair.txt") -> List()), Map(FileMetaData("bathroom.txt") -> List()))
      }

      "Link documents by their tags" in { f => pending }
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

