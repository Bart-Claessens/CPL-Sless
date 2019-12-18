package sless.ast

import sless.dsl.MergeDSL

trait Merge extends MergeDSL with Base with Property with Value with Rule with Selector {
  override def mergeSheets(cssSheets: Css*): Css =
    css(cssSheets.map(_.getRules).foldRight(Seq():Seq[Rule])((a,b)=>mergeRules(a,b,Seq())):_*)


  def mergeRules(rules1: Seq[Rule], rules2: Seq[Rule], result: Seq[Rule]): Seq[Rule] = rules2 match {
    case Seq() => rules1 match {
      case Seq() => result
      case _ => rules1.filterNot(r=>r.getSelector==Empty) ++ result
    }
    case _ => {
      val rule2 = rules2.last
      val (rs,ds) = rules1.map(mergeMapp(_,rule2)).unzip
      val rule3 = ds.foldRight(rule2)( addUniqueDeclarationsToRule)
      mergeRules(rs , rules2.dropRight(1) ,rule3 +: result)
    }
  }

  def addUniqueDeclarationsToRule(declarations: Seq[Declaration], rule: Rule): Rule = rule match {
    case CommentRule(s,d,comment) => {
      val unique = declarations.filterNot(x => rule.hasProperty(x.getProperty))
        CommentRule(s,d++unique,comment)
    }
  }

  def mergeMapp(left: Rule, right: Rule): (Rule, Seq[Declaration]) = left match {
    case CommentRule(sl,d, comment) => right match {
      case CommentRule(sr,_,_) => sl match {
        case GroupSelector(selectors) =>
          if (selectors.contains(sr)) {
            val sel = selectors.filter(_!=sr)
            val newsel = if (sel.isEmpty) Empty else GroupSelector(sel)
            (CommentRule(newsel,d,comment ), d)
          }
          else
            (left, Seq())
        case _ =>
          if (sl == sr) (CommentRule(Empty,d,comment), d)
          else (left, Seq())
      }
    }
  }

}
