package TagExposure

import CommonModels.{FileMetaData, Tag, TagLink}
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.wordspec.AnyWordSpec

class JsonParserSpec extends AnyWordSpec {

  markup {
    "Formats Tag Lists into Json Structures which can be sent via HTTP to frontend"
  }

  "TagExposure.JsonParserSpec" when {
    "Given a list of Json tags" should {
      "Format the tags into a Json Structure" in {
        val tagList: List[TagLink] = List(TagLink(Tag("[[furniture]]"), List(FileMetaData("sofa.txt"), FileMetaData("chair.txt"))),
          TagLink(Tag("[[cats]]"), List(FileMetaData("cat.txt"), FileMetaData("dog.txt"))))
        val json: String = """[
                             |  {
                             |    "tag" : {
                             |      "tag" : "[[furniture]]"
                             |    },
                             |    "files" : [
                             |      {
                             |        "name" : "sofa.txt"
                             |      },
                             |      {
                             |        "name" : "chair.txt"
                             |      }
                             |    ]
                             |  },
                             |  {
                             |    "tag" : {
                             |      "tag" : "[[cats]]"
                             |    },
                             |    "files" : [
                             |      {
                             |        "name" : "cat.txt"
                             |      },
                             |      {
                             |        "name" : "dog.txt"
                             |      }
                             |    ]
                             |  }
                             |]
                             |
                             |"""


        JsonParser.tagsToJson(tagList) shouldEqual json
      }
    }
  }
}
