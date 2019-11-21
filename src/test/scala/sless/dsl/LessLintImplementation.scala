package sless.dsl

import sless.ast.{Base, Compiler, Lint, Property, Selector, Value}
import sless.dsl.CssImplementation.DSL

object LessLintImplementation {
  type DSL = PropertyDSL with SelectorDSL with ValueDSL with LintDSL with Compilable
  object BaseC extends Base with Value with Property with Selector with Lint with Compiler
  val dsl: DSL = BaseC
}
