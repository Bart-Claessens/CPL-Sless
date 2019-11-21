package sless.ast

import sless.dsl.LintDSL

trait Lint extends LintDSL with Base {
  /**
    * Check if the given sheet has any style rules without declarations, i.e. of the form "selector {}"
    */
  override def removeEmptyRules(css: CssAST): (Boolean, CssAST) =
    ( css.rules.map(isEmptyRule).fold(false)(_ || _) , CssAST(css.rules.filterNot(isEmptyRule)))


  def isEmptyRule(rule: RuleAST): Boolean = rule match {
    case ARule(_,Nil) => true
    case CommentRule(r,_) => isEmptyRule(r)
    case _ => false
  }

  def dropEmptyRules(rules: Seq[Lint.this.RuleAST]): Seq[RuleAST] = rules.filterNot(r=>isEmptyRule(r))

  /**
    * Check if the given sheet has any style rules with a  declaration for all four properties from the set
    * margin-left, margin-right, margin-top, and margin-bottom, and if so, replaces each property by
    * the single shorthand property margin. The new margin property takes the place of the first declaration in order of appearance.
    * The values from the individual prorperties are aggregated in the order top-right-bottom-left, with spaces in between.
    */
  override def aggregateMargins(css: CssAST): (Boolean, CssAST) = ???


  /**
    * Check if the given sheet contains strictly more than n 'float' properties and, if so, returns true, otherwise false.
    */
  override def limitFloats(css: CssAST, n: Integer): Boolean = ???
}
