package NetGeneration

object Stemmer {
//Not using this right now

  def combineStems(nodes: List[String]): List[String] = {
    nodes.map(name => removeSuffix(name)).distinct
  }

  def removeSuffix(str: String): String = {
    str match {
      case str if str.endsWith("es") => str.dropRight(2)
      case str if str.endsWith("ed")
        && str(str.length -1) != 'a'
        && str(str.length -1) !='e'
        && str(str.length -1) !='i'
        && str(str.length -1) !='o'
        && str(str.length -1) !='u'
      => str.dropRight(2)
      case str if str.endsWith("s") && str(str.length -2) !='s' => str.dropRight(1)
      case str => str
    }
  }
}
