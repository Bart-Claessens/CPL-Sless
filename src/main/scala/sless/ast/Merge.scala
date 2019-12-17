package sless.ast

import sless.dsl.MergeDSL

trait Merge extends MergeDSL with Base with Property with Value with Rule with Selector {
  override def mergeSheets(cssSheets: Css*): Css =
    css(cssSheets.map(_.getRules).foldRight(Seq():Seq[Rule])(mergeRules):_*)


  def mergeRules(rules1: Seq[Rule], rules2: Seq[Rule]): Seq[Rule] = rules1 match {
    case Seq() => rules2
    case Seq(rule) => rule +: rules2
    case _ => {
      val rule1 = rules1.last
      rule1.getSelector match {
        case UniversalSelector => mergeRules(rules1.dropRight(1), rules2)
        case _ => {
          val (rs,ds) = rules1.dropRight(1).map(mergeMapp(_,rule1)).unzip
          val rule2 = ds.foldRight(rule1)( addUniqueDeclarationsToRule)
          mergeRules(rs ,  rule2 +: rules2)
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

}
