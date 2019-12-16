package sless.ast

import sless.dsl.MergeDSL

trait Merge extends MergeDSL with Base with Property with Value with Rule{
  override def mergeSheets(cssSheets: Css*): Css = css( cssSheets.map(_.getRules).fold(Seq())((r1,r2)=>mergeHelper(r1,r2)) :_* )
//    css(mergeHelper(cssSheets.map(c=>c.getRules)):_*)

//  def mergeHelper(ruleSeqs: Seq[Seq[Rule]]): Seq[Rule] = ???

  def mergeHelper(rules1: Seq[Rule], rules2: Seq[Rule]): Seq[Rule] = rules1 match {
    case Seq() => rules2
    case Seq(first, tail @ _*) =>
  }

  def merger(rule1: Rule, rules2: Seq[Rule]): Seq[Rule] =

  //mergeHelper(rules1 :+ first, tail)

//  def mergeHelper(rules1: Seq[Rule], rules2: Seq[Rule]): Seq[Rule] = for (rule1 <- rules1) {
//    for (rule2 <- rules2) {
//      if (rule1.getSelector.equals(rule2.getSelector)) rules2:+rule1
//    }
//  }
}
