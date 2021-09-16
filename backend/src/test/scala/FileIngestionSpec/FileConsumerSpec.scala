package FileIngestionSpec

import FileIngestion.LocalFileConsumer
import Utils.AtlasFileUtil
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class FileConsumerSpec extends AnyWordSpec with Matchers with BeforeAndAfter {

  markup {
    "FileConsumerSpec checks that the FileConsumer can list files from directories"
  }

  val utils = new AtlasFileUtil

  before {
    utils.allFileContents.map(pair => utils.createFileStructure(pair._1, pair._2))
  }

  after {
    utils.deleteAllFiles
  }

  "FileConsumerSpec" when {
    "Given a nested directory structure" should {
      "Return a list of files" in {
        LocalFileConsumer.isDirectory(utils.testingDirectory).getOrElse(fail("Cannot get top level")) should contain theSameElementsAs utils.allFiles
      }

      "Handle the case if no files are found" in {
        val emptyDirectoryStructure: String =
          "src/test/Resources/TestData/household/inhabitants"
        LocalFileConsumer.isDirectory(emptyDirectoryStructure) shouldBe None
      }
    }
  }
}
