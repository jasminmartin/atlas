package FileIngestion

import java.io.File

trait FileConsumer {
  def getFiles(topLevelDirectory: String, extensions: List[String]): List[File]
  def filterFileExtensions(fileList: List[File], extensions: List[String]): List[File]
}
