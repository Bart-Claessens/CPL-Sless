package sless.ast

trait Rule extends Base with Property {

  def isEmptyRule(rule: Rule): Boolean = rule match {
    case CommentRule(_, declarations, _) => declarations == Nil
  }

  def getRulesFrom(sheet: Css): Seq[Rule] = sheet match {
    case ACss(rules) => rules
  }

  def getSelectorFrom(rule: Rule): Selector = rule match {
    case CommentRule(s,_,_) => s
  }

  def getDeclarationsFrom(rule: Rule): Seq[Declaration] = rule match {
    case CommentRule(_,d,_) => d
  }

  def ruleHasProperty(rule: Rule, property: Property): Boolean = {
    rule.getDeclarations.map(_.hasProperty(property)).exists(b=>b)
  }

  def getValueOfPropertyFromRule(rule: Rule, property: Property): Option[Value] = rule match {
    case CommentRule(_,declarations,_) => {
      declarations.map(d=>d.getValueOfProperty(property)).foldLeft(None:Option[Value]){(o1,o2) => if (o1.isEmpty) o2 else o1}
    }
  }

  implicit class CssShorthand(c: Css) {
    def getRules: Seq[Rule] = getRulesFrom(c)
  }

  implicit class RuleShorthand(r: Rule) {
    def isEmpty: Boolean = isEmptyRule(r)
    def getSelector: Selector = getSelectorFrom(r)
    def getDeclarations = getDeclarationsFrom(r)
    def hasProperty(p: Property): Boolean = ruleHasProperty(r,p)
    def getValueOfProperty(p: Property): Option[Value] = getValueOfPropertyFromRule(r,p)
  }

}
