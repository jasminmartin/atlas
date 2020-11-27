import java.io.File

import scala.io.Source

object TagIdentifier {
  def findTag(file: File): List[Tag] = {
    val regex = """\[\[([^\[\]]+)\]\]""".r
    val stringFile: String = Source.fromFile(file).mkString
    regex.findAllMatchIn(stringFile).map(tag => Tag(tag.toString)).toList
  }

  def displayFileTags(files: List[File]): List[FileTags] = {
    files.map(file => FileTags(FileMetaData(file.getName), findTag(file)))
  }
}
