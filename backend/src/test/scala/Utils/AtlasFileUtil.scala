package Utils

import java.io.{File, PrintWriter}

class AtlasFileUtil {

  val (chair, chairContent) = ("src/test/Resources/Test/household/chair.txt", "[[chair]]")
  val (sofa, sofaContent)  = ("src/test/Resources/Test/household/sofa.txt", "[[sofa]]")
  val (bathroom, bathroomContent) = ("src/test/Resources/Test/household/rooms/bathroom.txt", "[[chair]] and [[sofa]]")
  val (cat, catContent)  = ("src/test/Resources/Test/household/pets/cat.txt", "[[cat]]")
  val (dog, dogContent)  = ("src/test/Resources/Test/household/pets/dog.txt", "[[dog]] and [[cat]]")
  val (lion, lionContent)  = ("src/test/Resources/Test/household/wildlife/lion.txt", "[[lion]] and [[cat]]")

  val allFileContents = List((chair, chairContent),(sofa, sofaContent),(bathroom, bathroomContent),(cat, catContent), (dog, dogContent),(lion, lionContent))

  val allFiles = allFileContents.map(pair => new File(pair._1))

  val testingDirectory = "src/test/Resources/Test/"
  def newFile(name: String): File = {
    val file = new File(name)
    file.getParentFile.mkdirs()
    file.createNewFile()
    file
  }

  def writeToFile(name: String, content: String) = {
    val writer = new PrintWriter(name, "UTF-8");
    writer.println(content);
    writer.close();
  }

  def file(name: String, content: String) = {
    newFile(name)
    writeToFile(name, content)
  }

  def createFileStructure(name: String, content: String) = {
    file(name, content)
  }

  def deleteAllFiles = {
    val filesToDelete =  new File(testingDirectory).listFiles()
    filesToDelete.map(file => file.delete())
  }
}
