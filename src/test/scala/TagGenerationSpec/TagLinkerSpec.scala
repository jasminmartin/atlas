package TagGenerationSpec

import CommonModels._
import TagGeneration.TagLinker
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TagLinkerSpec extends AnyWordSpec with Matchers {

  markup {
    "the tag linker spec links documents with the same tags"
  }

  "TagLinkerSpec" when {

    "Given a list of filetags" should {
      "Link documents with the same tags" in {
        val fileTags = List(
          FileAndTags(FileMetaData("running.txt"), List(Tag("trainers"), Tag("protein"), Tag("sprint"))),
          FileAndTags(FileMetaData("weightlifting.txt"), List(Tag("trainers"), Tag("protein"), Tag("strength"))),
          FileAndTags(FileMetaData("swimming.txt"), List(Tag("goggles"), Tag("protein"), Tag("water"))))

        TagLinker.linkDocsByTags(fileTags).right.get should contain theSameElementsAs
          (List(TagAndFiles(Tag("trainers"), List(FileMetaData("running.txt"), FileMetaData("weightlifting.txt"))),
            TagAndFiles(Tag("protein"), List(FileMetaData("running.txt"), FileMetaData("weightlifting.txt"), FileMetaData("swimming.txt")))))
      }

      "Return an CommonModels.NoLinksFound error if no links exist" in {
        val fileTags = List(
          FileAndTags(FileMetaData("running.txt"), List(Tag("whistle"), Tag("protein"), Tag("sprint"))),
          FileAndTags(FileMetaData("weightlifting.txt"), List(Tag("strong"), Tag("shoes"), Tag("strength"))),
          FileAndTags(FileMetaData("swimming.txt"), List(Tag("goggles"), Tag("swim"), Tag("water"))))

        TagLinker.linkDocsByTags(fileTags) shouldEqual Left(NoLinksFound(List(FileMetaData("running.txt"),
          FileMetaData("weightlifting.txt"), FileMetaData("swimming.txt"))))
      }
    }
  }
}
