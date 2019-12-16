package sless.ast

import sless.dsl.ValueDSL

trait Value extends ValueDSL with Base {
  override def value(string: String): Value = AValue(string)

  def value(int: Int): Value = AValue(int.toString)
}
