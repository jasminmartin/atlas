import java.io.File

class LocalFileConsumer extends FileConsumer {
  override def listFiles(topLevelDirectory: String): Option[List[File]] = {
    val d = new File(topLevelDirectory)
    if (d.exists && d.isDirectory) {
      Some(d.listFiles.filter(_.isFile).toList)
    } else {
      None
    }
  }

  def listFilesRecursive(topLevelDirectory: String): Option[List[File]] = {
    val topLevelDir = new File(topLevelDirectory)

        def getListOfFiles(dir: File): List[File] = {
          val filesList: List[File] = dir.listFiles.toList
          val res: List[File] = filesList ++ filesList.filter(_.isDirectory).flatMap(getListOfFiles)
          res.filter(_.isFile)
        }

        if (topLevelDir.exists() && topLevelDir.isDirectory) {
          Some(getListOfFiles(topLevelDir))
        }
        else None
      }


    override def filterFiles(fileList: List[File], extensions: List[String]): List[File] = {
      fileList.filter(f => f.getName.endsWith(extensions))
  }
}
