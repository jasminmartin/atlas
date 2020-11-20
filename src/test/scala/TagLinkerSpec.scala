import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.FixtureAnyWordSpec

class TagLinkerSpec extends FixtureAnyWordSpec with Matchers {
  markup {
    "the tag linker spec links documents with the same tags"
  }
  "TagLinkerSpec" when {
    "Given a list of filetags" should {
      "Link them" in { f => pending }
    }
  }

  case class FixtureParam()

  override protected def withFixture(test: OneArgTest) = {
    try {
      this.withFixture(test.toNoArgTest(
        FixtureParam()))
    }
  }
}
