

object TagLinker {
  def linkDocsByTags(fileTags: List[FileTags]): List[TagLink] = {
    val distinctTags: List[Tag] = findDistinctTags(fileTags)

    //for each unique tag, check if it is in a document
    //if it is, create a TagLink
    //Return all tag links
  }

  def findDistinctTags(fileTags: List[FileTags]): List[Tag] = {
    fileTags.flatMap(x => x.tags.distinct)
  }

  def isTagPresent(distinctTags: List[Tag], fileTags: List[FileTags]): List[TagLink] ={
//    distinctTags.map(unique => fileTags.map(x => x.tags).)
  }
}
