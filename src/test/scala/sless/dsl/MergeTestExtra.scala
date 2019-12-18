package sless.dsl

import org.scalatest.FunSuite

class MergeTestExtra extends  FunSuite{
  import MergeImplementation.dsl._

  /* Assumption: The structure of the sheet being merged into is maintained. This means that if a selector occurs
  identically twice (writing proper css/sless you wouldn't do that) in the sheet being merged into (rightmost sheet)
  then the rules with identical selector will not be merged.
  E.g. it will merge sheets into the rightmost sheet. Not the rightmost with itself.
  This will cause problematic behavior as seen in the "occurs twice" test. A solution would be to merge the rightmost
  sheet with itself first but this is not implemented as writing the same selector twice is bad practice */
  test("Occurs twice in rightmost") {
    val backgroundColor = prop("background-color")

    val ex1 = css(
      All.c("class-name1") (
        backgroundColor := value("blue"),
        prop("width") := value("95%")
      )
    )

    val ex2 = css(
      All.c("class-name1") (
        prop("width") := value("80%")
      ),
      All.c("class-name1") (
        backgroundColor := value("red"),
      )
    )

    val ex = mergeSheets(ex1,ex2)


    assert(
      MergeImplementation.dsl.compile(ex) ===
        """*.class-name1{width:80%;}*.class-name1{background-color:red;width:95%;}""")
  }

  test("Extended merge test") {
    val backgroundColor = prop("background-color")

    val ex1 = css(
      N(All.c("class-name1"), All.c("class-name2"), All.c("class-name3")) {
        prop("width") := value("100%")
      }
    )

    val ex2 = css(
      All.c("class-name1") (
        backgroundColor := value("blue"),
        prop("width") := value("95%")
      ),
      All.c("class-name2") (
        prop("height") := value("100%")
      )
    )

    val ex3 = css(
      All.c("class-name1") (
        backgroundColor := value("red"),
      )
    )

    val ex = mergeSheets(ex1,ex2,ex3)


    assert(
      MergeImplementation.dsl.compile(ex) ===
        """*.class-name3{width:100%;}*.class-name2{height:100%;width:100%;}*.class-name1{background-color:red;width:95%;}""")
  }

}
