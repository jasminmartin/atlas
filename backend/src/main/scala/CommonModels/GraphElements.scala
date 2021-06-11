package CommonModels

case class FileAndTags(file: String, tags: List[String])

case class Edge(firstNode: String, secondNode: String)

case class Graph(nodes: List[String], edges: List[Edge])

case class FileBody(name: String, body: String)
