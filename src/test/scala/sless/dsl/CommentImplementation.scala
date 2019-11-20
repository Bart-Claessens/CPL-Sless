package sless.dsl

import sless.ast.{Base, Compiler, Property, Selector, Value, Comment}
import sless.dsl.CssImplementation.DSL

object CommentImplementation {
  type DSL = PropertyDSL with SelectorDSL with ValueDSL with CommentDSL with Compilable
  object BaseC extends Base with Value with Property with Selector with Compiler with Comment
  val dsl: DSL = BaseC
}
