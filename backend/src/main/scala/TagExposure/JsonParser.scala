package TagExposure

import CommonModels.NodePair
import io.circe.Json
import io.circe.generic.auto._
import io.circe.syntax._


object JsonParser {

  def tagsToJson(tagLink: List[NodePair]): Json = {
    tagLink.asJson
  }
}
