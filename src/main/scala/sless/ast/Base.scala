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
  case class ARule(s: Selector, declarations: Seq[Declaration]) extends Rule
  case class CommentRule(r: Rule, comment: String) extends Rule

  sealed abstract class SelectorAST extends CompilableAST
  case object UniversalSelector extends  Selector
  case class TypeSelector(string: String) extends  Selector
  case class GroupSelector(selectors: Seq[Selector]) extends  Selector

  case class ClassNameSelector(s: Selector, string: String) extends  Selector
  case class IdSelector(s: Selector, string: String) extends  Selector
  case class AttributeSelector(s: Selector, attr: String, value: Value) extends  Selector

  case class PseudoClassSelector(s: Selector, string: String) extends  Selector
  case class PseudoElementSelector(s: Selector, string: String) extends  Selector

  sealed trait CombinatorSelector extends Selector {
    def s : Selector
    def selector : Selector
    def combinator: String
  }
  case class DescendantSelector(s: Selector, selector: Selector) extends  CombinatorSelector {
    override def combinator: String = " "
  }
  case class ChildSelector(s: Selector, selector: Selector) extends  CombinatorSelector {
    override def combinator: String = ">"
  }//(s,selector, ">")
  case class AdjacentSelector(s: Selector, selector: Selector) extends  CombinatorSelector {
    override def combinator: String = "+"
  }//(s,selector, "+")
  case class GeneralSelector(s: Selector, selector: Selector) extends  CombinatorSelector {
    override def combinator: String = "~"
  }//(s,selector, "~")

  object CombinatorSelector {
    def unapply(c: CombinatorSelector): Option[(Selector, Selector,String)] =
      Option(c) map { c =>
        (c.s, c.selector,c.combinator)
      }
  }
  sealed abstract class DeclarationAST extends CompilableAST
  case class ADeclaration(p: Property, value: Value) extends Declaration
  case class CommentDeclaration(d: Declaration, comment: String) extends Declaration

  sealed abstract class PropertyAST extends CompilableAST
  case class AProperty(value: String) extends Property

  sealed abstract class ValueAST extends CompilableAST
  case class AValue(value: String) extends Value








}
