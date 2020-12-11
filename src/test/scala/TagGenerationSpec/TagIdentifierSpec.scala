package TagGenerationSpec

import java.io.File

import CommonModels.{FileAndTags, FileMetaData, Tag}
import TagGeneration.TagIdentifier
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.FixtureAnyWordSpec

class TagIdentifierSpec extends FixtureAnyWordSpec with Matchers {

  markup {
    "TagIdentifierSpec ensures the tag identifier scans documents for tags which are identified by '[[]]"
  }

  "TagIdentifierSpec" when {
    "Given a single document" should {
      "Identify words in the '[[]]' tags" in { f =>
        TagIdentifier.tagsInFile(f.sofaFile) shouldEqual List(Tag("[[sitting]]"), Tag("[[furniture]]"))
      }

      "Associate the word in the '[[]]' tag to the document" in { f =>
        TagIdentifier.fileAndTags(List(f.sofaFile)) shouldEqual
          List(FileAndTags(FileMetaData("sofa.txt"), List(Tag("[[sitting]]"), Tag("[[furniture]]"))))
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
