package FileIngestionSpec

import FileIngestion.LocalFileParser
import Utils.AtlasFileUtil
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class FileConsumerSpec
    extends AnyWordSpec
    with Matchers
    with BeforeAndAfterEach {

  markup {
    "FileConsumerSpec checks that the FileConsumer can list files from directories"
  }

  val utils = new AtlasFileUtil

  override def beforeEach {
    utils.allFileContents.map(pair =>
      utils.createFileStructure(pair._1, pair._2)
    )
  }

  override def afterEach {
    utils.deleteFilesInTestinDir
  }

  "FileConsumerSpec" when {
    "Given a nested directory structure" should {
      "Return a list of files" in {
        LocalFileParser
          .isDirectory(utils.testingDirectory)
          .getOrElse(
            fail("Cannot get top level")
          ) should contain theSameElementsAs utils.allFiles
      }

      "Handle the case if no files are found" in {
        val emptyDirectoryStructure: String =
          "src/test/Resources/TestData/household/inhabitants"
        LocalFileParser.isDirectory(emptyDirectoryStructure) shouldBe None
      }
    }
  }
}
