package TagExposure

import CommonModels.TagLink
import io.circe.Json
import io.circe.generic.auto._
import io.circe.syntax._

object JsonParser {
  def tagsToJson(tagLink: List[TagLink]): Json = {
    println(tagLink.asJson)
    tagLink.asJson
  }
}
