package sless.dsl

import org.scalatest.FunSuite

class MixinTestExtra extends FunSuite {
  import MixinImplementation.dsl._
  import MixinImplementation._

  test("Simple color mixin") {
    val mixin = simpleColorMixin
    val propertiesPre = List(prop("property-pre") := value("value-pre"))
    val propertiesPost = List(prop("property-post") := value("value-post"))
    val rule = All.mixin(propertiesPre,mixin, propertiesPost)
    val ex = css(rule)

    assert(
      MixinImplementation.dsl.compile(ex) ===
        """*{property-pre:value-pre;color:red;background:white;property-post:value-post;}""")
  }

  test("Nested margin") {
    val mixin = nestedMixin
    val propertiesPre = List(prop("property-pre") := value("value-pre"))
    val propertiesPost = List(prop("property-post") := value("value-post"))
    val rule = All.mixin(propertiesPre,mixin(simpleColorMixin,"10px"), propertiesPost)
    val ex = css(rule)

    assert(
      MixinImplementation.dsl.compile(ex) ===
        """*{property-pre:value-pre;color:red;background:white;margin:10px;property-post:value-post;}""")
  }

  test("Height and double width") {
    val mixin = doubledWidthMixin
    val propertiesPre = List(prop("property-pre") := value("value-pre"))
    val propertiesPost = List(prop("property-post") := value("value-post"))
    val rule = All.mixin(propertiesPre,mixin(5), propertiesPost)
    val ex = css(rule)

    assert(
      MixinImplementation.dsl.compile(ex) ===
        """*{property-pre:value-pre;height:5;width:10;property-post:value-post;}""")
  }
}
