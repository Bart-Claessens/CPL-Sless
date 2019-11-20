package sless.ast

import sless.dsl.ValueDSL

trait Value extends ValueDSL with Base {
  override def value(string: String): ValueAST = AValue(string)
}
