package sless.ast

import sless.dsl.LintDSL

trait Lint extends LintDSL with Base {
  /**
    * Check if the given sheet has any style rules without declarations, i.e. of the form "selector {}"
    */
  override def removeEmptyRules(css: CssAST): (Boolean, CssAST) =
    ( css.rules.exists(isEmptyRule) , CssAST(css.rules.filterNot(isEmptyRule)))


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
  override def aggregateMargins(css: CssAST): (Boolean, CssAST) =
      ( css.rules.exists(hasAllMargins)  , CssAST(Nil) )

    def hasAllMargins(rule: RuleAST) = hasAllProperties(rule, marginList)

    def hasAllProperties(rule: RuleAST, props: List[String]): Boolean =
      props.map(p=>hasProperty(rule,p)).fold(true)(_ && _)

    def hasProperty(rule: RuleAST, prop: String): Boolean = rule match {
      case ARule(_,Nil) => false
      case CommentRule(r,comment) => hasProperty(r,prop)
      case ARule(s, declarations) => declarations.exists(d=>hasProperty(d,prop))
    }

    def hasProperty(declaration: DeclarationAST, prop: String): Boolean = declaration match {
//      case ADeclaration(Nil,_)  => false
      case CommentDeclaration(d,comment) => hasProperty(d,prop)
      case ADeclaration(AProperty(pvalue), dvalue) => pvalue.equals(prop)
    }


    val marginList = List("margin-top", "margin-right", "margin-bottom", "margin-left")


  /**
    * Check if the given sheet contains strictly more than n 'float' properties and, if so, returns true, otherwise false.
    */
  override def limitFloats(css: CssAST, n: Integer): Boolean = ???
}
