package sless.ast

import sless.dsl.Compilable

trait Compiler extends Compilable with Base {
  override def compile(sheet: CssAST): String = compileAST(sheet)

  override def pretty(sheet: CssAST, spaces: Int): String = prettyHelper(sheet,spaces)

  def compileAST(compilable: CompilableAST): String =  compilable match {
    case CssAST(rules) => rules.map(c => compileAST(c)).mkString("")
    case RuleAST(s,declarations) => compileAST(s) + "{" + declarations.map(d => compileAST(d)).mkString("") + "}"
    case DeclarationAST(p, value) => compileAST(p)+ ":" + compileAST(value) + ";"
    case ValueAST(value) => value
    case PropertyAST(value) => value
    case UniversalSelector() => "*"
    case TypeSelector(tipe) => tipe
    case GroupSelector(selectors) => selectors.map(s => compileAST(s)).mkString(",")
    case ClassNameSelector(s,className) => compileAST(s) + "." + className
    case IdSelector(s,id) => compileAST(s) + "#" + id
    case PseudoClassSelector(s,pseudoClass) => compileAST(s) + ":" + pseudoClass
  }

  def prettyHelper(compilable: CompilableAST, spaces: Int): String =  compilable match {
    case CssAST(rules) => rules.map(c => prettyHelper(c,spaces)).mkString("\n\n")
    case RuleAST(s, declarations)
      => prettyHelper(s,spaces) + " {\n" + declarations.map(d => prettyHelper(d,spaces)).mkString("\n") + "\n}"
    case DeclarationAST(p, value) => " " * spaces + prettyHelper(p,spaces) + ": " + prettyHelper(value,spaces) + ";"
    case ValueAST(value) => value
    case PropertyAST(value) => value
    case UniversalSelector() => "*"
    case TypeSelector(tipe) => tipe
    case GroupSelector(selectors) => selectors.map(s => prettyHelper(s,spaces)).mkString(", ")
    case ClassNameSelector(s, className) => prettyHelper(s,spaces) + "." + className
    case IdSelector(s, id) => prettyHelper(s,spaces) + "#" + id
    case PseudoClassSelector(s, pseudoClass) => prettyHelper(s,spaces) + ":" + pseudoClass
  }
}
//case class UniversalSelector() extends  SelectorAST
//case class TypeSelector(string: String) extends  SelectorAST
//case class GroupSelector(selectors: Seq[SelectorAST]) extends  SelectorAST
//
//case class ClassNameSelector(s: SelectorAST, string: String) extends  SelectorAST
//case class IdSelector(s: SelectorAST, string: String) extends  SelectorAST
//case class AttributeSelector(s: SelectorAST, attr: String, value: ValueAST) extends  SelectorAST
//
//case class PseudoClassSelector(s: SelectorAST, string: String) extends  SelectorAST
//case class PseudoElementSelector(s: SelectorAST, string: String) extends  SelectorAST
//
//case class DescendantSelector(s: SelectorAST, selector: SelectorAST) extends  SelectorAST
//case class ChildSelector(s: SelectorAST, selector: SelectorAST) extends  SelectorAST
//case class AdjacentSelector(s: SelectorAST, selector: SelectorAST) extends  SelectorAST
//case class GeneralSelector(s: SelectorAST, selector: SelectorAST) extends  SelectorAST
