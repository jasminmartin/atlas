import java.io.File

object LocalFileConsumer extends FileConsumer {

  override def listFiles(topLevelDirectory: String): Option[List[File]] = {
    val topLevelDir = new File(topLevelDirectory)
    topLevelDir match {
      case topLevelDir if (topLevelDir.exists() && topLevelDir.isDirectory) => Some(recursiveFileFetch(topLevelDir))
      case _ => None
    }
  }

  private def recursiveFileFetch(dir: File): List[File] = {
    val filesList: List[File] = dir.listFiles.toList
    val recList: List[File] = filesList ++ filesList.filter(_.isDirectory).flatMap(recursiveFileFetch)
    recList.filter(_.isFile)
  }

  override def filterFiles(fileList: List[File], extensions: List[String]): List[File] = {
    fileList.filter(f => f.getName.endsWith(extensions))
  }
}
