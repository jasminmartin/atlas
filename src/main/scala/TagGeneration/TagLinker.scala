package TagGeneration

import CommonModels.{FileMetaData, FileTags, NoLinksFound, Tag, TagLink}

import scala.collection.mutable.ListBuffer

object TagLinker {
  def linkDocsByTags(fileTags: List[FileTags]): Either[NoLinksFound, List[TagLink]] = {
    val distinctTags: List[Tag] = findDistinctTags(fileTags)
    val allLinks = distinctTags.map(distinctTag => isTagPresent(distinctTag, fileTags))
    println(identifyUniqueTagLinks(allLinks))
    identifyUniqueTagLinks(allLinks)
  }

  def findDistinctTags(fileTags: List[FileTags]): List[Tag] = {
    fileTags.flatMap(x => x.tags.distinct)
  }

  def isTagPresent(distinctTag: Tag, fileTags: List[FileTags]): TagLink = {
    val filesWithTag = new ListBuffer[FileMetaData]
    fileTags.map(doc => doc.tags.map(tag => if (tag == distinctTag) {
      filesWithTag += doc.fileMetaData
    })
    )
    TagLink(distinctTag, filesWithTag.toList)
  }

  def identifyUniqueTagLinks(tagLink: List[TagLink]): Either[NoLinksFound, List[TagLink]] = {
    val moreThanOneDocTagged = new ListBuffer[TagLink]
    tagLink.map(doc => if (doc.files.length > 1) {
      moreThanOneDocTagged += doc
    })
    moreThanOneDocTagged.toList.distinct match {
      case Seq() => Left(NoLinksFound(tagLink.flatMap(tag => tag.files).distinct))
      case x: List[TagLink] => Right(x)
    }
  }
}
