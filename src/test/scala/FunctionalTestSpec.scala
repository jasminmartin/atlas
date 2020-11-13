import java.io.File

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
        println(allFiles.get)
        val a: List[File]  = LocalFileConsumer.filterFiles(allFiles.get, List(".txt"))
        println(a)
        val fileTagList = TagIdentifier.displayFileTags(a)
        println(fileTagList)
        fileTagList shouldEqual "hi"
      }

      "Link documents by their tags" in { f => (pending) }
    }
  }

  case class FixtureParam(nestedDirectoryStructure: String)

  override protected def withFixture(test: OneArgTest) = {
    val nestedDirectoryStructure: String = ("src/test/Resources")

    try {
      this.withFixture(test.toNoArgTest(FixtureParam(nestedDirectoryStructure: String)))
    }
  }

}

