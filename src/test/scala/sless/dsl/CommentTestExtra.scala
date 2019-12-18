package sless.dsl

import org.scalatest.FunSuite

class CommentTestExtra extends FunSuite {
  import CommentImplementation.dsl._

  test("Adding second comment removes first") {
    val backgroundColor = prop("background-color")
    val container = tipe("div") ## "container"

    /* Assumption: only one comment per rule/declaration */
    val ex = css(
      (N(All.c("class-name1"), All.c("class-name2")) {
        prop("width") := value("100%")
      }).comment( "something with class 1 and 2").comment("this comment overwrites the previous one"),
      container {
        (backgroundColor := value("blue")).comment("bg is blue")
      }
    )

    assert(
      compile(ex) ===
        """/* this comment overwrites the previous one */*.class-name1,*.class-name2{width:100%;}div#container{background-color:blue;/* bg is blue */}""")

  }
}
