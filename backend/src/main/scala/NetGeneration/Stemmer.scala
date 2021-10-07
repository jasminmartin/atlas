package NetGeneration

object Stemmer {
//Not using this right now

  def stem(word: String): String = {
    val s1a: String = step1a(word.toLowerCase())
    println(s1a)
    val s1b = step1b(s1a)
    println(s1b)
    val s1c = step1c(s1b)
    println(s1c)
    val s2 = step2(s1c)
    println(s2)
    val s3 = step3(s2)
    println(s3)
    val s4 = step4(s3)
    println(s4)
    val s5a = step5a(s4)
    println(s5a)
    val s5b = step5b(s5a)
    println(s5b)
    s5b
  }

  def step1a(word: String): String = {
    word match {
      case word if word.endsWith("es") => word.dropRight(2)
      case word if word.endsWith("ies") => word.dropRight(2)
      case word if word.endsWith("ss") => word
      case word if word.endsWith("s") => word.dropRight(1)
      case _ => word
    }
  }

    def step1b(word: String): String = {
      if (word.endsWith("eed") && calculateM(word.dropRight(3)) > 0) {
        word.dropRight(1)
      }
        val stemmed = word match {
          case word if (word.endsWith("ed") && word.map(l => consOrVowel(l)).toList.contains('v')) => word.dropRight(2)
          case word if (word.endsWith("ing") && word.map(l => consOrVowel(l)).toList.contains('v')) => word.dropRight(3)
          case _ => return word
        }
        stemmed match {
          case stemmed if (stemmed.endsWith("at")) => stemmed +'e'
          case stemmed if (stemmed.endsWith("bl")) => stemmed +'e'
          case stemmed if (stemmed.endsWith("iz")) => stemmed +'e'
          case stemmed if (stemmed.charAt(stemmed.length-1) == stemmed.charAt(stemmed.length-2) &&
            (consOrVowel(stemmed.charAt(stemmed.length)) == 'c' &&
              (consOrVowel(stemmed.charAt(stemmed.length-1)) == 'c' &&
                stemmed.charAt(stemmed.length) != 'l' &&
                stemmed.charAt(stemmed.length) != 's' &&
                stemmed.charAt(stemmed.length) != 'z'
                ))) => stemmed.dropRight(1)
          case stemmed if (calculateM(stemmed) > 0 &&
            stemmed.map(letter => consOrVowel(letter)).endsWith("cvc")) &&
          !(stemmed.endsWith("x")) &&
            !(stemmed.endsWith("y")) &&
            !(stemmed.endsWith("z")) => stemmed+'e'
          case _ => stemmed
        }
      }

  def step1c(word: String) = {
    if(consOrVowel(word.charAt(word.length -1)) == 'v' && word.charAt(word.length-1)== 'y') {
      word.dropRight(1) + 'i'
    } else word
  }

  def step2(word: String) = {
    word match {
      case word if (word.endsWith("ational") && calculateM(word.dropRight(7)) > 0) => word.dropRight(7) + "ational"
      case word if (word.endsWith("entli") && calculateM(word.dropRight(5)) > 0) => word.dropRight(5) + "entli"
      case word if (word.endsWith("anci") && calculateM(word.dropRight(4)) > 0) => word.dropRight(4) + "anci"
      case word if (word.endsWith("izer") && calculateM(word.dropRight(4)) > 0) => word.dropRight(4) + "izer"
      case word if (word.endsWith("abli") && calculateM(word.dropRight(4)) > 0) => word.dropRight(4) + "abli"
      case word if (word.endsWith("alli") && calculateM(word.dropRight(4)) > 0) => word.dropRight(4) + "alli"
      case word if (word.endsWith("eli") && calculateM(word.dropRight(3)) > 0) => word.dropRight(3) + "eli"
      case word if (word.endsWith("ousli") && calculateM(word.dropRight(5)) > 0) => word.dropRight(5) + "ousli"
      case word if (word.endsWith("ization") && calculateM(word.dropRight(7)) > 0) => word.dropRight(7) + "ization"
      case word if (word.endsWith("ation") && calculateM(word.dropRight(5)) > 0) => word.dropRight(5) + "ation"
      case word if (word.endsWith("ator") && calculateM(word.dropRight(4)) > 0) => word.dropRight(4) + "ator"
      case word if (word.endsWith("alism") && calculateM(word.dropRight(5)) > 0) => word.dropRight(5) + "alism"
      case word if (word.endsWith("iveness") && calculateM(word.dropRight(7)) > 0) => word.dropRight(7) + "iveness"
      case word if (word.endsWith("fulness") && calculateM(word.dropRight(7)) > 0) => word.dropRight(7) + "fulness"
      case word if (word.endsWith("ousness") && calculateM(word.dropRight(7)) > 0) => word.dropRight(7) + "ousness"
      case word if (word.endsWith("ivili") && calculateM(word.dropRight(5)) > 0) => word.dropRight(5) + "ivili"
      case word if (word.endsWith("biliti") && calculateM(word.dropRight(6)) > 0) => word.dropRight(6) + "biliti"
      case _ => word
    }
  }

  def step3(word: String) = {
    word match {
      case word if (word.endsWith("icate") && calculateM(word.dropRight(5)) > 0) => word.dropRight(5) + "ic"
      case word if (word.endsWith("ative") && calculateM(word.dropRight(5)) > 0) => word.dropRight(5)
      case word if (word.endsWith("alize") && calculateM(word.dropRight(5)) > 0) => word.dropRight(5) +"al"
      case word if (word.endsWith("iciti") && calculateM(word.dropRight(5)) > 0) => word.dropRight(5) +"ic"
      case word if (word.endsWith("ical") && calculateM(word.dropRight(4)) > 0) => word.dropRight(4) + "ic"
      case word if (word.endsWith("full") && calculateM(word.dropRight(4)) > 0) => word.dropRight(4)
      case word if (word.endsWith("ness") && calculateM(word.dropRight(4)) > 0) => word.dropRight(4)
      case _ => word
    }
  }

  def step4(word: String) = {
    word match {
      case word if (word.endsWith("al") && calculateM(word.dropRight(2)) > 1) => word.dropRight(2)
      case word if (word.endsWith("ance") && calculateM(word.dropRight(4)) > 1) => word.dropRight(4)
      case word if (word.endsWith("ence") && calculateM(word.dropRight(4)) > 1) => word.dropRight(4)
      case word if (word.endsWith("er") && calculateM(word.dropRight(2)) > 1) => word.dropRight(2)
      case word if (word.endsWith("ic") && calculateM(word.dropRight(2)) > 1) => word.dropRight(2)
      case word if (word.endsWith("able") && calculateM(word.dropRight(4)) > 1) => word.dropRight(4)
      case word if (word.endsWith("ible") && calculateM(word.dropRight(4)) > 1) => word.dropRight(4)
      case word if (word.endsWith("ant") && calculateM(word.dropRight(3)) > 1) => word.dropRight(3)
      case word if (word.endsWith("ement") && calculateM(word.dropRight(4)) > 1) => word.dropRight(4)
      case word if (word.endsWith("ment") && calculateM(word.dropRight(4)) > 1) => word.dropRight(4)
      case word if (word.endsWith("ent") && calculateM(word.dropRight(3)) > 1) => word.dropRight(3)
      case word if (word.endsWith("ion") && (word.charAt(word.length-5) == 's' || word.charAt(word.length-5) == 't') && calculateM(word.dropRight(4)) > 1) => word.dropRight(4)
      case word if (word.endsWith("ou") && calculateM(word.dropRight(2)) > 1) => word.dropRight(2)
      case word if (word.endsWith("ism") && calculateM(word.dropRight(3)) > 1) => word.dropRight(3)
      case word if (word.endsWith("ate") && calculateM(word.dropRight(3)) > 1) => word.dropRight(3)
      case word if (word.endsWith("iti") && calculateM(word.dropRight(3)) > 1) => word.dropRight(3)
      case word if (word.endsWith("ous") && calculateM(word.dropRight(3)) > 1) => word.dropRight(3)
      case word if (word.endsWith("ive") && calculateM(word.dropRight(3)) > 1) => word.dropRight(3)
      case word if (word.endsWith("ize") && calculateM(word.dropRight(3)) > 1) => word.dropRight(3)
      case _ => word
    }}

  def step5a(word: String): String = {
    if (!word.endsWith("e")) {
      return word
    }
    val withoutE = word.dropRight(1)
    withoutE match {
      case word if (calculateM(word.dropRight(1)) > 1) => withoutE
      case word if (calculateM(word) == 1 &&
        withoutE.map(letter => consOrVowel(letter)).endsWith("cvc")) &&
        !(withoutE.endsWith("x")) &&
        !(withoutE.endsWith("y")) &&
        !(withoutE.endsWith("z")) => withoutE
      case _ => word
    }
  }

  def step5b(word: String) = {
    word match {
      case word if (calculateM(word) > 1 &&
        word.map(letter => consOrVowel(letter)).endsWith("cc")) &&
        word.charAt(word.length - 1) == 'l' => word.dropRight(1)
      case _ => word
    }
  }

    def calculateM(word: String): Int = {
      val pattern: String = cvPattern(word)
      var measure = 0
      var remaining = pattern

      while (remaining.startsWith("C")) {
        remaining = remaining.substring(1)
      }

      while (remaining.startsWith("VC")) {
        measure += 1
        remaining = remaining.substring(2)
      }
      measure
    }

    def consOrVowel(char: Char): Char = {
      if (Set('a', 'A', 'e', 'E', 'i', 'I', 'o', 'O', 'u', 'U').contains(char)) {
        'v'
      } else 'c'
    }

    def cvPattern(word: String)={
      val cvPattern: String = word.map(letter => consOrVowel(letter))
      simplifyCVPattern(cvPattern)
    }

    def simplifyCVPattern(cvPattern: String): String = {
      if (cvPattern.isEmpty){ return "" }

      var reduced = cvPattern(0).toString
      var last = cvPattern(0)
      for (l <- cvPattern) {
        if (l != last) {
          reduced += l;
        }
        last = l
      }
      reduced.toUpperCase()
  }
}
