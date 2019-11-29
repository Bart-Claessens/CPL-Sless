package sless.ast

import sless.dsl.LintDSL

trait Lint extends LintDSL with Base with Property with Value {
  /**
    * Check if the given sheet has any style rules without declarations, i.e. of the form "selector {}"
    */
  override def removeEmptyRules(css: CssAST): (Boolean, CssAST) =
    ( css.rules.exists(isEmptyRule) , fromRules(css.rules.filterNot(isEmptyRule)))


  def isEmptyRule(rule: RuleAST): Boolean = rule match {
    case ARule(_,declarations) => declarations == Nil
    case CommentRule(r,_) => isEmptyRule(r)
  }


  /**
    * Check if the given sheet has any style rules with a  declaration for all four properties from the set
    * margin-left, margin-right, margin-top, and margin-bottom, and if so, replaces each property by
    * the single shorthand property margin. The new margin property takes the place of the first declaration in order of appearance.
    * The values from the individual prorperties are aggregated in the order top-right-bottom-left, with spaces in between.
    */
  override def aggregateMargins(css: CssAST): (Boolean, CssAST) = {
    val mappedRules = css.rules.map (aggregateMargins)
    val bool = mappedRules.foldLeft(false){ case(a, (b, _)) => b || a   }
    val rules = mappedRules.foldLeft(Seq():Seq[RuleAST]){(a,b) => a:+b._2}
    (bool, fromRules(rules))
  }

  val marginList = List("margin-top", "margin-right", "margin-bottom", "margin-left")

  def aggregateMargins(r: RuleAST): (Boolean, RuleAST) = r match {
    case CommentRule(r1,comment) =>
      val (aggregated, r2) = aggregateMargins(r1)
      (aggregated, CommentRule(r2, comment) )
    case ARule(s,declarations) =>
      val mappedMargins = marginList.map(m=>declarations.map(d=>getValueOfProperty(d,m)).fold(None)(reduceOption))
      val hasNotAll: Boolean = mappedMargins.exists(_.isEmpty)
      if (hasNotAll) (false, r) else {
        val indexFirst: Int = declarations.indexWhere(d=>marginList.contains(getProperty(d)))
        val dmargin = prop("margin")  := value(mappedMargins.flatten.mkString(" "))
        val d2 = declarations.updated(indexFirst,dmargin).filterNot(d=>marginList.contains(getProperty(d)))
        (true, ARule(s,d2))
      }
  }

  def hasProperty(declaration: DeclarationAST, prop: String): Boolean = declaration match {
    case CommentDeclaration(d,_) => hasProperty(d,prop)
    case ADeclaration(AProperty(pvalue), _) => pvalue == prop
  }

  def getValueOfProperty(d: DeclarationAST, property: String): Option[String] = d match {
    case ADeclaration(AProperty(pvalue), AValue(value)) => if (pvalue.equals(property)) Some(value) else None
    case CommentDeclaration(d, _) => getValueOfProperty(d,property)
  }

  def getProperty(d: DeclarationAST): String = d match {
    case ADeclaration(AProperty(pvalue), _) => pvalue
    case CommentDeclaration(d, _) => getProperty(d)
  }

  def reduceOption(opt1: Option[String], opt2: Option[String] ): Option[String] = opt1 match {
    case None => opt2
    case Some(_) => opt1
  }




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

