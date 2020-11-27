sealed case class Errors()

case class NoLinksFound(documentsScanned: List[FileMetaData])
