package sless.ast

import sless.dsl.Compilable

trait Compiler extends Compilable with Base {
  override def compile(sheet: CssAST): String = compileAST(sheet)

  override def pretty(sheet: CssAST, spaces: Int): String = prettyHelper(sheet,spaces)

  def compileAST(compilable: CompilableAST): String =  compilable match {
    case CssAST(rules) => rules.map(c => compileAST(c)).mkString("")
    case ARule(s,declarations) => compileAST(s) + "{" + declarations.map(d => compileAST(d)).mkString("") + "}"
    case ADeclaration(p, value) => compileAST(p)+ ":" + compileAST(value) + ";"
    case AValue(value) => value
    case AProperty(value) => value
    case UniversalSelector() => "*"
    case TypeSelector(tipe) => tipe
    case GroupSelector(selectors) => selectors.map(s => compileAST(s)).mkString(",")
    case ClassNameSelector(s,className) => compileAST(s) + "." + className
    case IdSelector(s,id) => compileAST(s) + "#" + id
    case PseudoClassSelector(s,pseudoClass) => compileAST(s) + ":" + pseudoClass
    case PseudoElementSelector(s,psuedoElement) =>compileAST(s) + "::" + psuedoElement
    case DescendantSelector(s,selector) => compileAST(s) + " " + compileAST(selector)
    case ChildSelector(s,selector) => compileAST(s) + ">" + compileAST(selector)
    case AdjacentSelector(s,selector) => compileAST(s) + "+" + compileAST(selector)
    case GeneralSelector(s,selector) => compileAST(s) + "~" + compileAST(selector)
    case CommentDeclaration(d,comment) => compileAST(d) + "/* " + comment + " */"
    case CommentRule(r,comment) => "/* " + comment + " */" + compileAST(r)
  }

  def prettyHelper(compilable: CompilableAST, spaces: Int): String =  compilable match {
    case CssAST(rules) => rules.map(c => prettyHelper(c,spaces)).mkString("\n\n")
    case ARule(s, declarations)
      => prettyHelper(s,spaces) + " {\n" + declarations.map(d => prettyHelper(d,spaces)).mkString("\n") + "\n}"
    case ADeclaration(p, value) => " " * spaces + prettyHelper(p,spaces) + ": " + prettyHelper(value,spaces) + ";"
    case AValue(value) => value
    case AProperty(value) => value
    case UniversalSelector() => "*"
    case TypeSelector(tipe) => tipe
    case GroupSelector(selectors) => selectors.map(s => prettyHelper(s,spaces)).mkString(", ")
    case ClassNameSelector(s, className) => prettyHelper(s,spaces) + "." + className
    case IdSelector(s, id) => prettyHelper(s,spaces) + "#" + id
    case PseudoClassSelector(s, pseudoClass) => prettyHelper(s,spaces) + ":" + pseudoClass
    case PseudoElementSelector(s, pseudoElement) => prettyHelper(s,spaces) + "::" + pseudoElement
    case DescendantSelector(s,selector) => prettyHelper(s,spaces) + " " + prettyHelper(selector,spaces)
    case ChildSelector(s,selector) => prettyHelper(s,spaces) + " > " + prettyHelper(selector,spaces)
    case AdjacentSelector(s,selector) => prettyHelper(s,spaces) + " + " + prettyHelper(selector,spaces)
    case GeneralSelector(s,selector) => prettyHelper(s,spaces) + " ~ " + prettyHelper(selector,spaces)
  }
}
