package TagGeneration

import CommonModels._

import scala.collection.mutable.ListBuffer

object TagLinker {
  def linkDocsByTags(fileAndTags: List[FileAndTags]): Either[NoLinksFound, List[TagAndFiles]] = {
    val distinctTags: List[Tag] = allTagsInSystem(fileAndTags)
    val tagsAndFiles: List[TagAndFiles] = distinctTags.map(distinctTag => isTagPresent(distinctTag, fileAndTags))
    identifyUniqueTagLinks(tagsAndFiles)
  }

  def allTagsInSystem(fileTags: List[FileAndTags]): List[Tag] = {
    fileTags.flatMap(x => x.tags.distinct)
  }

  def isTagPresent(distinctTag: Tag, fileTags: List[FileAndTags]): TagAndFiles = {
    val filesLinkedToTag = new ListBuffer[FileMetaData]
    fileTags.map(doc => doc.tags.map(tag => if (tag == distinctTag) {
      filesLinkedToTag += doc.fileMetaData
    })
    )
    TagAndFiles(distinctTag, filesLinkedToTag.toList)
  }

  def identifyUniqueTagLinks(tagLink: List[TagAndFiles]): Either[NoLinksFound, List[TagAndFiles]] = {
    val moreThanOneDocTagged = new ListBuffer[TagAndFiles]
    tagLink.map(doc => if (doc.files.length > 1) {
      moreThanOneDocTagged += doc
    })
    moreThanOneDocTagged.toList.distinct match {
      case List() => Left(NoLinksFound(tagLink.flatMap(tag => tag.files).distinct))
      case linkedFiles: List[TagAndFiles] => Right(linkedFiles)
    }
  }
}
