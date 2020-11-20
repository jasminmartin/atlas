import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.FixtureAnyWordSpec

class TagLinkerSpec extends FixtureAnyWordSpec with Matchers {
  markup {
    "the tag linker spec links documents with the same tags"
  }
  "TagLinkerSpec" when {
    "Given a list of filetags" should {
      "Link documents with the same tags" in { f =>
        val fileTags = List(
          FileTags(FileMetaData("running.txt"), List(Tag("trainers"), Tag("protein"), Tag("sprint"))),
          FileTags(FileMetaData("weightlifting.txt"), List(Tag("trainers"), Tag("protein"), Tag("strength"))),
          FileTags(FileMetaData("swimming.txt"), List(Tag("goggles"), Tag("protein"), Tag("water"))))

        TagLinker.linkdocsbytags(fileTags: List[FileTags]) should contain theSameElementsAs
          List(TagLink(Tag("trainers"), List(FileMetaData("running.txt"), FileMetaData("weightlifting.txt"))),
            TagLink(Tag("protein"), List(FileMetaData("running.txt"), FileMetaData("weightlifting.txt"), FileMetaData("swimming.txt"))))
      }
    }
  }

  case class FixtureParam()

  override protected def withFixture(test: OneArgTest) = {
    try {
      this.withFixture(test.toNoArgTest(
        FixtureParam()))
    }
  }
}
