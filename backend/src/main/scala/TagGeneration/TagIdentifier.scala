package TagGeneration

import java.io.File

import CommonModels.{FileMetaData, FileAndTags, Tag}

import scala.io.Source

object TagIdentifier {
  def tagsInFile(file: File): List[Tag] = {
    val tagRegex = """\[\[([^\[\]]+)\]\]""".r
    val bufferFile = Source.fromFile(file)
    val stringFile = bufferFile.mkString
    bufferFile.close()
    tagRegex.findAllMatchIn(stringFile).map(tag => Tag(tag.toString)).toList
  }

  def fileAndTags(files: List[File]): List[FileAndTags] = {
    val allFileAndTags: List[FileAndTags] = files.map(file => FileAndTags(FileMetaData(file.getName), tagsInFile(file)))
    allFileAndTags.sortWith(_.tags.length < _.tags.length)
  }
}
