import java.io.File

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.FixtureAnyWordSpec

import scala.Console.in

class FileConsumerSpec extends FixtureAnyWordSpec with Matchers {

  markup {
    "FileConsumerSpec checks that the FileConsumer can list files from directories"
  }

  "FileConsumer" when {
    "Given a flat file directory structure" should {
      "Return a list of the files it contains" in { f =>
        LocalFileConsumer.listFiles(f.flatDirectoryStructure) contains f.flatDirectoryStructureFiles
      }
      "Filter out non-text files" in { f =>
        val allFiles: Option[List[File]] = LocalFileConsumer.listFiles(f.flatDirectoryStructure)
        LocalFileConsumer.filterFiles(allFiles.get, List(".txt")) contains f.flatDirectoryStructureTxtFiles
      }
      "Handle the case if no files are found" in { f =>
        val emptyDirectoryStructure: String = ("src/test/Resources/household/inhabitants")
        LocalFileConsumer.listFiles(emptyDirectoryStructure) shouldBe None
      }
      "Handle the case if a file does not have accessible permissions" in { f => (pending) }
    }

    "Given a nested file directory structure" should {
      "Return a list of the files contained in all directories" in { f =>
        LocalFileConsumer.listFiles(f.nestedDirectoryStructure) contains f.nestedDirectoryStructureFiles
      }
      "Filter out non-text files" in { f =>
        val allFiles: Option[List[File]] = LocalFileConsumer.listFiles(f.nestedDirectoryStructure)
        LocalFileConsumer.filterFiles(allFiles.get, List(".txt")) contains f.nestedDirectoryStructureTxtFiles
      }
      "Handle the case if no files are found" in { f =>
        val emptyDirectoryStructure: String = ("src/test/Resources/household/inhabitants")
        LocalFileConsumer.listFiles(emptyDirectoryStructure) shouldBe None
      }
    }
  }

  override protected def withFixture(test: OneArgTest) = {

    val flatDirectoryStructure: String = ("src/test/Resources/household")

    val catPicture = new File("src/test/Resources/household/catPicture.png")
    val chair = new File("src/test/Resources/household/chair.txt")
    val sofa = new File("src/test/Resources/household/sofa.txt")
    val bathroom = new File("src/test/Resources/household/rooms/bathroom")
    val dogPicture = new File("src/test/Resources/household/rooms/dog.png")
    val cat = new File("src/test/Resources/cat.txt")
    val dog = new File("src/test/Resources/dog.txt")


    val flatDirectoryStructureFiles: List[File] = List(
      chair, sofa, catPicture
    )

    val flatDirectoryStructureTxtFiles: List[File] = List(
      chair, sofa
    )

    val nestedDirectoryStructure: String = ("src/test/Resources")

    val nestedDirectoryStructureFiles: List[File] = List(
      chair, sofa, catPicture, bathroom, dogPicture, dog, cat
    )

    val nestedDirectoryStructureTxtFiles: List[File] = List(
      chair, sofa, dog, cat
    )


    try {
      this.withFixture(test.toNoArgTest(
        FixtureParam(flatDirectoryStructureFiles: List[File],
          flatDirectoryStructure: String,
          flatDirectoryStructureTxtFiles: List[File],
          nestedDirectoryStructure: String,
          nestedDirectoryStructureFiles: List[File],
          nestedDirectoryStructureTxtFiles: List[File])))
    }
  }

  case class FixtureParam(flatDirectoryStructureFiles: List[File],
                          flatDirectoryStructure: String,
                          flatDirectoryStructureTxtFiles: List[File],
                          nestedDirectoryStructure: String,
                          nestedDirectoryStructureFiles: List[File],
                          nestedDirectoryStructureTxtFiles: List[File])

}
