package CommonModels

case class FileAndNodes(file: String, tags: List[String])

case class Edge(firstNode: String, secondNode: String)

case class Graph(nodes: List[String], edges: List[Edge])

