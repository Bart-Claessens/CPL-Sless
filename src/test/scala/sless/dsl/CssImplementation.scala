package sless.dsl

import sless.ast.{Base, Property, Selector, Value, Compiler}

object CssImplementation {
  type DSL = PropertyDSL with SelectorDSL with ValueDSL with Compilable
  object BaseC extends Base with Value with Property with Selector with Compiler
  val dsl: DSL = BaseC
}
