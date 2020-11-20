import java.io.File

import scala.collection.View.Filter
import scala.io.Source

object TagIdentifier {
  def findTag(file: File): List[Tag] = {
    val regex = """\[\[([^\[\]]+)\]\]""".r
    val stringFile: String = Source.fromFile(file).mkString
    println("hi" + stringFile)
    regex.findAllMatchIn(stringFile).map(x => Tag(x.toString)).toList
  }

  def displayFileTags(files: List[File]): List[FileTags] = {
    files.map(x => FileTags(FileMetaData(x.getName), findTag(x)))
  }
}
