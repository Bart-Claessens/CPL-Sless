package sless.ast

import sless.dsl.ValueDSL

trait Value extends ValueDSL with Base {
  override def value(string: String): Value = AValue(string)
}
