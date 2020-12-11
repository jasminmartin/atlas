package CommonModels

case class Tag(tag: String)

case class FileMetaData(name: String)

case class FileAndTags(fileMetaData: FileMetaData, tags: List[Tag])

case class TagAndFiles(tag: Tag, files: List[FileMetaData])
