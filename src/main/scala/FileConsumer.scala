import java.io.File

trait FileConsumer {
  def listFiles(topLevelDirectory: String): Option[List[File]]
  def filterFiles(fileList: List[File], extensions: List[String]): List[File]
}
