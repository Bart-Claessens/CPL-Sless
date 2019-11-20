package sless.ast

import sless.dsl.{BaseDSL, Compilable, PropertyDSL, SelectorDSL, ValueDSL}


trait Base extends BaseDSL {
  override type Rule = RuleAST
  override type Css = CssAST
  override type Selector = SelectorAST
  override type Declaration = DeclarationAST
  override type Property = PropertyAST
  override type Value = ValueAST

  protected def fromRules(rules: Seq[Rule]): CssAST = CssAST(rules)

  sealed abstract class CompilableAST
  sealed abstract class SelectorAST extends CompilableAST
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

  case class CssAST(rules : Seq[RuleAST]) extends CompilableAST

  case class RuleAST(s: SelectorAST, declarations: Seq[DeclarationAST]) extends CompilableAST

  case class DeclarationAST(p: PropertyAST, value: ValueAST) extends CompilableAST

  case class PropertyAST(value: String) extends CompilableAST

  case class ValueAST(value: String) extends CompilableAST








}
