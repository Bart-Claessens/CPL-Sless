package sless.ast

import sless.dsl.LintDSL

trait Lint extends LintDSL with Base with Property with Value with Rule {
  /**
    * Check if the given sheet has any style rules without declarations, i.e. of the form "selector {}"
    */
  override def removeEmptyRules(sheet: Css): (Boolean, Css) = ???
//    ( sheet.getRules.exists(_.isEmpty) , css(sheet.getRules.filterNot(_.isEmpty):_*))


  /**
    * Check if the given sheet has any style rules with a  declaration for all four properties from the set
    * margin-left, margin-right, margin-top, and margin-bottom, and if so, replaces each property by
    * the single shorthand property margin. The new margin property takes the place of the first declaration in order of appearance.
    * The values from the individual prorperties are aggregated in the order top-right-bottom-left, with spaces in between.
    */
  override def aggregateMargins(sheet: Css): (Boolean, Css) = {
    val mappedRules = sheet.getRules.map(aggregateMargins)
    val bool = mappedRules.foldLeft(false){ (a,b) => a || b._1 }
    val rules = mappedRules.foldLeft(Seq():Seq[Rule]){ (a, b) => a :+ b._2 }
    (bool, css(rules:_*))
  }

  val marginList = List("margin-top", "margin-right", "margin-bottom", "margin-left")

  def aggregateMargins(r: Rule): (Boolean, Rule) = ??? //r match {
//    case CommentRule(r1,comment) =>
//      val (isAggregated, r2) = aggregateMargins(r1)
//      (isAggregated, CommentRule(r2, comment) )
//    case ARule(s,declarations) =>
//      val mappedMargins = marginList.map(m=>declarations.map(d=>d.getValueOfProperty(m))
//        .fold(None){(o1,o2) => if (o1.isEmpty) o2 else o1})
//      val hasNotAll: Boolean = mappedMargins.exists(_.isEmpty)
//      if (hasNotAll) (false, r) else {
//        val indexFirst: Int = declarations.indexWhere(d=>marginList.contains(d.getProperty))
//        val dMargin = prop("margin") := value(mappedMargins.flatten.mkString(" "))
//        val aggregated = declarations.updated(indexFirst,dMargin).filterNot(d=>marginList.contains(d.getProperty))
//        (true, ARule(s,aggregated))
//      }
//  }


  /**
    * Check if the given sheet contains strictly more than n 'float' properties and, if so, returns true, otherwise false.
    */
  override def limitFloats(css: Css, n: Integer): Boolean = ???
//    n < css.getRules.map( r => r.mapDeclarations({ d => if (d.hasProperty("float")) 1 else 0 }).sum ).sum


  //    n < countFloats(css)

//  def countFloats(compilable: CompilableAST): Int = compilable match {
//    case CssAST(rules) => rules.map(countFloats).sum
//    case CommentRule(r,_) => countFloats(r)
//    case ARule(_, declarations) => declarations.map(countFloats).sum
//    case CommentDeclaration(d,_) => countFloats(d)
//    case ADeclaration(p, _) => countFloats(p)
//    case AProperty(value) => if (value == "float") 1 else 0
//    case _ => 0
//  }
}

