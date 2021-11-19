package Utils

import java.io.{File, PrintWriter}

class AtlasFileUtil {
  val testingDirectory = "src/test/Resources/Test/"

  val chair = "chair"
  val sofa = "sofa"
  val bathroom = "bathroom"
  val cat = "cat"
  val dog = "dog"
  val lion = "lion"

  def tag(word: String) = {
    s"[[${word}]]"
  }

  val allWords = List(chair, sofa, bathroom, cat, dog, lion)

  val (chairPath, chairContent) = ("src/test/Resources/Test/household/chair.txt", tag(chair))
  val (sofaPath, sofaContent)  = ("src/test/Resources/Test/household/sofa.txt", tag(sofa))
  val (bathroomPath, bathroomContent) = ("src/test/Resources/Test/household/rooms/bathroom.txt", tag(bathroom))
  val (catPath, catContent)  = ("src/test/Resources/Test/household/pets/cat.txt", tag(cat))
  val (dogPath, dogContent)  = ("src/test/Resources/Test/household/pets/dog.txt", tag(dog))
  val (lionPath, lionContent)  = ("src/test/Resources/Test/household/wildlife/lion.txt", tag(lion))

  val allFileContents = List((chairPath, chairContent),(sofaPath, sofaContent),(bathroomPath, bathroomContent),(catPath, catContent), (dogPath, dogContent),(lionPath, lionContent))

  val allFiles = allFileContents.map(pair => new File(pair._1))


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

  def deleteFilesInTestinDir = {
    deleteFilesIn(new File(testingDirectory))
  }
  def deleteFilesIn(directory: File): Unit = {
    val filesToDelete =  directory.listFiles()
    filesToDelete.foreach(file => {
      if (file.isDirectory) {
        deleteFilesIn(file) }
      file.delete()
    })}
}
