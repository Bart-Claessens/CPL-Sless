package sless.ast

import sless.dsl.LintDSL

trait Lint extends LintDSL with Base with Property with Value {
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
      ( css.rules.exists(hasAllMargins)  , CssAST(css.rules.map(transformRule(_,marginList))) )


  def hasAllMargins(rule: RuleAST): Boolean = hasAllProperties(rule, marginList)

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

  def getValue(d: DeclarationAST, prop: String): Option[String] = d match {
    case ADeclaration(AProperty(pvalue), AValue(value)) => if (pvalue.equals(prop)) Some(value) else None
    case CommentDeclaration(d, comment) => getValue(d,prop)
  }

  def getProperty(d: DeclarationAST): String = d match {
    case ADeclaration(AProperty(pvalue), _) => pvalue
    case CommentDeclaration(d, comment) => getProperty(d)
  }

  def getValue(declarations: Seq[DeclarationAST], prop: String) : Option[String] =
    declarations.map(getValue(_,prop)).fold(None)(reduceOption)

  def transformRule(r: RuleAST, props: List[String]): RuleAST = r match {
    case CommentRule(r1,comment) => CommentRule(transformRule(r1,props), comment)
    case ARule(s,declarations) =>
      val mappedMargins = props.map(getValue(declarations,_))
      val hasNotAll: Boolean = mappedMargins.exists(_.isEmpty)
      if (hasNotAll) r else {
        val indexFirst: Int = declarations.indexWhere(d=>props.contains(getProperty(d)))
        val margindDecl = prop("margin")  := value(mappedMargins.flatten.mkString(" "))
        val d2 = declarations.updated(indexFirst,margindDecl).filterNot(d=>props.contains(getProperty(d)))
        ARule(s,d2)
      }
  }

  def reduceOption(opt1: Option[String], opt2: Option[String] ): Option[String] = opt1 match {
    case None => opt2
    case Some(_) => opt1
  }

    val marginList = List("margin-top", "margin-right", "margin-bottom", "margin-left")


  /**
    * Check if the given sheet contains strictly more than n 'float' properties and, if so, returns true, otherwise false.
    */
  override def limitFloats(css: CssAST, n: Integer): Boolean = n < countFloats(css)

  def countFloats(compilable: CompilableAST): Int = compilable match {
    case CssAST(rules) => rules.map(countFloats).sum
    case CommentRule(r,_) => countFloats(r)
    case ARule(_, declarations) => declarations.map(countFloats).sum
    case CommentDeclaration(d,_) => countFloats(d)
    case ADeclaration(p, _) => countFloats(p)
    case AProperty(value) => if (value == "float") 1 else 0
    case _ => 0
  }
}

