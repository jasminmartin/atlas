package NetGenerationSpec

import CommonModels._
import FileIngestion.{FileConsumer, LocalFileConsumer}
import NetGeneration._
import Utils.AtlasFileUtil
import org.scalatest.concurrent.Eventually.eventually
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.{AnyWordSpec, FixtureAnyWordSpec}
import org.scalatest.{BeforeAndAfter, BeforeAndAfterEach, Outcome}

import java.io.File

class NodeIdentifierSpec extends AnyWordSpec with Matchers with BeforeAndAfterEach {

  markup {
    "NodeIdentifierSpec ensures the Node identifier scans documents for Nodes which are identified by '[[]]"
  }

  val utils = new AtlasFileUtil

  override def beforeEach {
    utils.allFileContents.map(pair => utils.createFileStructure(pair._1, pair._2))
  }

  override def afterEach {
    utils.deleteFilesInTestinDir
    Thread.sleep(1000)
  }

  "NodeIdentifierSpec" when {
    "Given documents" should {
      "Identify nodes tagged in '[[]]'" in {
        utils.allFiles.flatMap(file => NodeIdentifier.findTaggedNodes(file)) shouldEqual utils.allWords
      }

      "Associate the tagged nodes '[[]]' to the file Node" in {
        NodeIdentifier.createNodePairs(utils.allFiles) shouldEqual utils.allWords.map(word => FileAndTags(word, List(word)))
      }

      "Update nodes with new body information" in {
        val graph = new GraphCreator(LocalFileConsumer, utils.testingDirectory)
        graph.updateFileBody(utils.sofa, "[[Furniture]]")
        NodeIdentifier.findTaggedNodes(utils.newFile(utils.sofaPath)) shouldEqual List("Furniture")
      }
    }
  }
}
