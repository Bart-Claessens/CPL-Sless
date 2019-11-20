package sless.ast

import sless.dsl.SelectorDSL

trait Selector extends SelectorDSL with Base {
  override protected def className(s: SelectorAST, string: String): SelectorAST = ClassNameSelector(s,string)

  override protected def id(s: SelectorAST, string: String): SelectorAST = IdSelector(s,string)

  override protected def attribute(s: SelectorAST, attr: String, value: ValueAST): SelectorAST = AttributeSelector(s,attr,value)

  override protected def pseudoClass(s: SelectorAST, string: String): SelectorAST = PseudoClassSelector(s,string)

  override protected def pseudoElement(s: SelectorAST, string: String): SelectorAST = PseudoElementSelector(s,string)

  /** -> s + selector { ... } */
  override protected def adjacent(s: SelectorAST, selector: SelectorAST): SelectorAST = AdjacentSelector(s,selector)

  /** -> s ~ selector { ... } */
  override protected def general(s: SelectorAST, selector: SelectorAST): SelectorAST = GeneralSelector(s,selector)

  /** -> s > selector { ... } */
  override protected def child(s: SelectorAST, selector: SelectorAST): SelectorAST = ChildSelector(s,selector)

  /** -> s selector { ... } */
  override protected def descendant(s: SelectorAST, selector: SelectorAST): SelectorAST = DescendantSelector(s,selector)

  override protected def group(selectors: Seq[SelectorAST]): SelectorAST = GroupSelector(selectors)

  override def tipe(string: String): SelectorAST = TypeSelector(string)

  override val All: SelectorAST = UniversalSelector()

  override protected def bindTo(s: SelectorAST, declarations: Seq[DeclarationAST]): RuleAST = RuleAST(s,declarations)
}
