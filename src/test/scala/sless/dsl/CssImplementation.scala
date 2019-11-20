package sless.dsl

import sless.ast.Base

object CssImplementation {
  type DSL = PropertyDSL with SelectorDSL with ValueDSL with Compilable
  object BaseC extends Base
  val dsl: DSL = BaseC
}
