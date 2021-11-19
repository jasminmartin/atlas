package NetGenerationSpec

import FileIngestion.FileParser
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
      "Stem step 1a paper examples" in { f =>
        {
          Stemmer.step1a("caresses") shouldBe "caress"
          Stemmer.step1a("ponies") shouldBe "poni"
          Stemmer.step1a("ties") shouldBe "ti"
          Stemmer.step1a("cats") shouldBe "cat"
        }
      }
      "Stem step 1b paper examples" in { f =>
        {
          Stemmer.step1b("agreed") shouldBe "agree"
          Stemmer.step1b("plastered") shouldBe "plaster"
          Stemmer.step1b("motoring") shouldBe "motor"
          Stemmer.step1b("feed") shouldBe "feed"
          Stemmer.step1b("size") shouldBe "size"
          Stemmer.step1b("fizzed") shouldBe "fizz"
        }
      }
      "Stem step 1c paper examples" in { f =>
        {
          Stemmer.step1c("happy") shouldBe "happi"
          Stemmer.step1c("sky") shouldBe "sky"
        }
      }
      "Stem step 2 paper examples" in { f =>
        {
          Stemmer.step2("relational") shouldBe "relate"
          Stemmer.step2("conditional") shouldBe "condition"
          Stemmer.step2("rational") shouldBe "rational"
          Stemmer.step2("formaliti") shouldBe "formal"
        }
      }
      "Stem step 3 paper examples" in { f =>
        {
          Stemmer.step3("triplicate") shouldBe "triplic"
          Stemmer.step3("formative") shouldBe "form"
          Stemmer.step3("formalize") shouldBe "formal"
          Stemmer.step3("electrical") shouldBe "electric"
        }
      }
      "Stem step 4 paper examples" in { f =>
        {
          Stemmer.step4("revival") shouldBe "reviv"
          Stemmer.step4("allowance") shouldBe "allow"
          Stemmer.step4("inference") shouldBe "infer"
          Stemmer.step4("adjustable") shouldBe "adjust"
        }
      }
      "Stem step 5A paper examples" in { f =>
        {
          Stemmer.step5a("probate") shouldBe "probat"
          Stemmer.step5a("rate") shouldBe "rat"
          Stemmer.step5a("cease") shouldBe "cease"
        }
      }
      "Stem step 5B paper examples" in { f =>
        {
          Stemmer.step5b("controll") shouldBe "control"
          Stemmer.step5b("roll") shouldBe "roll"
        }
      }
      "Utilise the full stemmer correctly" in { f =>
        {
          Stemmer.stem("multidimensional") shouldBe "multidimension"
          Stemmer.stem("characterization") shouldBe "character"
          Stemmer.stem("sleeping") shouldBe "sleep"
          Stemmer.stem("spinned") shouldBe "spin"
        }
      }
    }
  }

  case class FixtureParam(
      sofaFile: File,
      mockFileConsumer: FileParser,
      textCreator: GraphCreator
  )

  override protected def withFixture(test: OneArgTest): Outcome = {
    val sofaFile = new File("src/test/Resources/TestData/household/sofa.txt")
    val testSource = "src/test/Resources/TestData/household"
    val mockFileConsumer = mock[FileParser]
    val textCreator = new GraphCreator(mockFileConsumer, testSource)
    try {
      this.withFixture(
        test.toNoArgTest(FixtureParam(sofaFile, mockFileConsumer, textCreator))
      )
    }
  }
}
