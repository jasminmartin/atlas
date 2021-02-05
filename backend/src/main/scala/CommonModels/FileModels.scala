package CommonModels

case class Node(tag: String)

//case class FileMetaData(name: String)

//case class FileAndTags(fileMetaData: FileMetaData, tags: List[Node])

case class NodePair(file: Node, tags: List[Node])

case class Edge(file: Node, tag: String)

//case class TagAndFiles(tag: Node, files: List[FileMetaData])
