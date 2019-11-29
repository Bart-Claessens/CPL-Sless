package sless.ast

import sless.dsl.SelectorDSL

trait Selector extends SelectorDSL with Base {
  override protected def className(s: Selector, string: String): Selector = ClassNameSelector(s,string)

  override protected def id(s: Selector, string: String): Selector = IdSelector(s,string)

  override protected def attribute(s: Selector, attr: String, value: Value): Selector = AttributeSelector(s,attr,value)

  override protected def pseudoClass(s: Selector, string: String): Selector = PseudoClassSelector(s,string)

  override protected def pseudoElement(s: Selector, string: String): Selector = PseudoElementSelector(s,string)

  /** -> s + selector { ... } */
  override protected def adjacent(s: Selector, selector: Selector): Selector = AdjacentSelector(s,selector)

  /** -> s ~ selector { ... } */
  override protected def general(s: Selector, selector: Selector): Selector = GeneralSelector(s,selector)

  /** -> s > selector { ... } */
  override protected def child(s: Selector, selector: Selector): Selector = ChildSelector(s,selector)

  /** -> s selector { ... } */
  override protected def descendant(s: Selector, selector: Selector): Selector = DescendantSelector(s,selector)

  override protected def group(selectors: Seq[Selector]): Selector = GroupSelector(selectors)

  override def tipe(string: String): Selector = TypeSelector(string)

  override val All: Selector = UniversalSelector

  override protected def bindTo(s: Selector, declarations: Seq[Declaration]): Rule = ARule(s,declarations)
}
