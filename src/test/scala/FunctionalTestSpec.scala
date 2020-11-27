import java.io.File

import CommonModels.{FileMetaData, FileTags, Tag, TagLink}
import FileIngestion.LocalFileConsumer
import TagGeneration.{TagIdentifier, TagLinker}
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
        val fileTagList: List[FileTags] = TagIdentifier.displayFileTags(filteredFiles)
        fileTagList should contain theSameElementsAs
          List(FileTags(FileMetaData("dog.txt"),List()), FileTags(FileMetaData("cat.txt"),List()), FileTags(FileMetaData("sofa.txt"),List(Tag("[[sitting]]"), Tag("[[furniture]]"))), FileTags(FileMetaData("chair.txt"),List(Tag("[[furniture]]"))), FileTags(FileMetaData("bathroom.txt"),List()))       }

      "Link documents by their tags" in { f =>
        val allFiles: Option[List[File]] = LocalFileConsumer.listFiles(f.nestedDirectoryStructure)
        val filteredFiles: List[File] = LocalFileConsumer.filterFiles(allFiles.get, List(".txt"))
        val fileTagList: List[FileTags] = TagIdentifier.displayFileTags(filteredFiles)
        TagLinker.linkDocsByTags(fileTagList) shouldEqual Right(List(TagLink(Tag("[[furniture]]"),List(FileMetaData("sofa.txt"), FileMetaData("chair.txt")))))
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

