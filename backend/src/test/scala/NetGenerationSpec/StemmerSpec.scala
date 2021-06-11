package NetGenerationSpec

import FileIngestion.FileConsumer
import NetGeneration._
import org.mockito.MockitoSugar.mock
import org.scalatest.Outcome
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.FixtureAnyWordSpec

import java.io.File

class StemmerSpec extends FixtureAnyWordSpec with Matchers {

  markup {
    "NodeIdentifierSpec ensures the Node identifier scans documents for Nodes which are identified by '[[]]"
  }

  "StemmerSpec" when {
      "On attempting stemming" should {
        "Normalise the stem" in { f => {
          Stemmer.removeSuffix("Misses") shouldEqual "Miss"
          Stemmer.removeSuffix("Missed") shouldEqual "Miss"
          Stemmer.removeSuffix("Miss") shouldEqual "Miss"
          Stemmer.removeSuffix("Sleeps") shouldEqual "Sleep"
        }}
        "Store distinct stemmed names" in { f =>
          Stemmer.combineStems(List("Stressed", "Stresses", "Bird", "Birds", "Potatos", "Potato")) shouldEqual List("Stress", "Bird", "Potato")
        }
      }
    }

  case class FixtureParam(sofaFile: File, mockFileConsumer: FileConsumer, textCreator: GraphCreator)

  override protected def withFixture(test: OneArgTest): Outcome = {
    val sofaFile = new File("src/test/Resources/TestData/household/sofa.txt")
    val testSource = "src/test/Resources/TestData/household"
    val mockFileConsumer = mock[FileConsumer]
    val textCreator = new GraphCreator(mockFileConsumer, testSource)
    try {
      this.withFixture(test.toNoArgTest(
        FixtureParam(sofaFile, mockFileConsumer, textCreator)))
    }
  }
}
