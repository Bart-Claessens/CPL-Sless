package sless.dsl

import sless.ast.{Base, Compiler, Merge, Property, Selector, Value}
import sless.dsl.LessVariableImplementation.DSL

object MergeImplementation {
  type DSL = PropertyDSL with SelectorDSL with ValueDSL with MergeDSL with Compilable
  object BaseC extends Base with Property with Selector with Value with Merge with Compiler
  val dsl: DSL = BaseC
}
