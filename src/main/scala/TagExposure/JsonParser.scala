package TagExposure

import CommonModels.TagAndFiles
import io.circe.Json
import io.circe.generic.auto._
import io.circe.syntax._


object JsonParser {

  def tagsToJson(tagLink: List[TagAndFiles]): Json = {
    tagLink.asJson
  }
}
