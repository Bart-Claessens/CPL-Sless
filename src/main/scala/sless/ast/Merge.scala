package sless.ast

import sless.dsl.MergeDSL

trait Merge extends MergeDSL with Base with Property with Value with Rule with Selector {
  override def mergeSheets(cssSheets: Css*): Css = css(mergeRules(cssSheets.map(css=>css.getRules).flatten):_*)

  //css( cssSheets.map(_.getRules).fold(Seq())((r1,r2)=>mergeHelper(r1,r2)) :_* )
//    css(mergeHelper(cssSheets.map(c=>c.getRules)):_*)

//  def mergeHelper(ruleSeqs: Seq[Seq[Rule]]): Seq[Rule] = ???

//  def mergeHelper(rules1: Seq[Rule], rules2: Seq[Rule]): Seq[Rule] = rules1 match {
//    case Seq() => rules2
//    case Seq(first, tail @ _*) => mergeHelper(tail, merger(first,rules2 ))
//  }
//
//  def merger(rule1: Rule, rules2: Seq[Rule]): Seq[Rule] = {
//    val indexr2 = rules2.indexWhere( r=>equals(r.getSelector,rule1.getSelector))
//    if (indexr2 == -1) rule1 +: rules2 else {
//      val r2 = rules2.take(indexr2)
//    }
//  }
  def mergeRules(rules: Seq[Rule]): Seq[Rule] = mergeHelper(rules, Seq())._2

  def mergeHelper(rules: Seq[Rule], newrules: Seq[Rule]): (Seq[Rule],Seq[Rule]) = rules match {
    case Seq() => (rules,newrules)
    case Seq(rule) => (Seq() ,  rule +: newrules)
    case _ => {
      val rule1 = rules.last
      rule1.getSelector match {
        case UniversalSelector => (rules.dropRight(1), newrules)
        case _ => {
          val (rs,ds) = rules.dropRight(1).map(mergeMapp(_,rule1)).unzip
          val rule2 = ds.foldRight(rule1)( addUniqueDeclarationsToRule)
          mergeHelper(rs ,  rule2 +: newrules)
        }
      }
    }
  }

  def addUniqueDeclarationsToRule( ds: Seq[Declaration], rule: Rule): Rule = rule match {
    case CommentRule(s,d,comment) => CommentRule(s,d++ds,comment)
  }

  def mergeMapp(left: Rule, right: Rule): (Rule, Seq[Declaration]) = right match {
    case CommentRule(sr,d, comment) => {
      val sl = left.getSelector
      if (sl == sr) (CommentRule(All,d,comment), d)
      else (right, Seq())
    }
  }

   //mergeHelper(rules1 :+ first, tail)

//  def mergeHelper(rules1: Seq[Rule], rules2: Seq[Rule]): Seq[Rule] = for (rule1 <- rules1) {
//    for (rule2 <- rules2) {
//      if (rule1.getSelector.equals(rule2.getSelector)) rules2:+rule1
//    }
//  }
}
