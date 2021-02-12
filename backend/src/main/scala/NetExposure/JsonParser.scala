package NetExposure

import CommonModels.FileAndNodes
import io.circe.Json
import io.circe.generic.auto._
import io.circe.syntax._


object JsonParser {

  def tagsToJson(tagLink: List[FileAndNodes]): Json = {
    tagLink.asJson
  }
}
