case class Tag(tag: String)

case class FileMetaData(name: String)

case class FileTags(fileMetaData: FileMetaData, tags: List[Tag])
