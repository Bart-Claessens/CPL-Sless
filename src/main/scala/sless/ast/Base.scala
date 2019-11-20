package sless.ast

import sless.dsl.{BaseDSL, Compilable, PropertyDSL, SelectorDSL, ValueDSL}


class Base extends BaseDSL with ValueDSL with PropertyDSL with SelectorDSL with Compilable{
  override type Rule = RuleAST
  override type Css = CssAST
  override type Selector = SelectorAST
  override type Declaration = DeclarationAST
  override type Property = PropertyAST
  override type Value = ValueAST

  protected def fromRules(rules: Seq[Rule]): CssAST = CssAST(rules)

  sealed trait SelectorAST
  case class UniversalSelector() extends  SelectorAST
  case class TypeSelector(string: String) extends  SelectorAST
  case class GroupSelector(selectors: Seq[SelectorAST]) extends  SelectorAST

  case class ClassNameSelector(s: SelectorAST, string: String) extends  SelectorAST
  case class IdSelector(s: SelectorAST, string: String) extends  SelectorAST
  case class AttributeSelector(s: SelectorAST, attr: String, value: ValueAST) extends  SelectorAST

  case class PseudoClassSelector(s: SelectorAST, string: String) extends  SelectorAST
  case class PseudoElementSelector(s: SelectorAST, string: String) extends  SelectorAST

  case class DescendantSelector(s: SelectorAST, selector: SelectorAST) extends  SelectorAST
  case class ChildSelector(s: SelectorAST, selector: SelectorAST) extends  SelectorAST
  case class AdjacentSelector(s: SelectorAST, selector: SelectorAST) extends  SelectorAST
  case class GeneralSelector(s: SelectorAST, selector: SelectorAST) extends  SelectorAST

  case class CssAST(rules : Seq[RuleAST])

  case class RuleAST(s: SelectorAST, declarations: Seq[DeclarationAST])

  case class DeclarationAST(p: PropertyAST, value: ValueAST)

  case class PropertyAST(value: String)

  case class ValueAST(value: String)

  override def value(string: String): ValueAST = ValueAST(string)

  override def prop(string: String): PropertyAST = PropertyAST(string)

  override protected def assign(p: PropertyAST, value: ValueAST): DeclarationAST = DeclarationAST(p,value)

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

  override def compile(sheet: CssAST): String = """*.class-name1,*.class-name2{width:100%;}div#container{background-color:blue;}div#container:hover{background-color:red;}"""

  override def pretty(sheet: CssAST, spaces: Int): String = ""
}
