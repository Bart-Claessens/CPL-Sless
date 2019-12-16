package sless.ast

import sless.dsl.{BaseDSL, Compilable, PropertyDSL, SelectorDSL, ValueDSL}


trait Base extends BaseDSL {
  override type Rule = RuleAST
  override type Css = CssAST
  override type Selector = SelectorAST
  override type Declaration = DeclarationAST
  override type Property = PropertyAST
  override type Value = ValueAST

  protected def fromRules(rules: Seq[Rule]): Css = ACss(rules)

  sealed abstract class CompilableAST

  sealed abstract class CssAST extends CompilableAST
  case class ACss(rules : Seq[Rule]) extends Css

  sealed abstract class RuleAST extends CompilableAST
  //case class ARule(s: Selector, declarations: Seq[Declaration]) extends Rule
  case class CommentRule(s: Selector, declarations: Seq[Declaration], comment: String) extends Rule

  sealed abstract class SelectorAST extends CompilableAST
  case object UniversalSelector extends  Selector
  case class TypeSelector(string: String) extends  Selector
  case class GroupSelector(selectors: Seq[Selector]) extends  Selector

  case class ClassNameSelector(s: Selector, string: String) extends  Selector
  case class IdSelector(s: Selector, string: String) extends  Selector
  case class AttributeSelector(s: Selector, attr: String, value: Value) extends  Selector

  case class PseudoClassSelector(s: Selector, string: String) extends  Selector
  case class PseudoElementSelector(s: Selector, string: String) extends  Selector

  case class DescendantSelector(s: Selector, selector: Selector) extends  Selector
  case class ChildSelector(s: Selector, selector: Selector) extends  Selector
  case class AdjacentSelector(s: Selector, selector: Selector) extends  Selector
  case class GeneralSelector(s: Selector, selector: Selector) extends  Selector

  sealed abstract class DeclarationAST extends CompilableAST
  //case class ADeclaration(p: Property, value: Value) extends Declaration
  case class CommentDeclaration(p: Property, value: Value, comment: String) extends Declaration

  sealed abstract class PropertyAST extends CompilableAST
  case class AProperty(value: String) extends Property

  sealed abstract class ValueAST extends CompilableAST
  case class AValue(value: String) extends Value








}
