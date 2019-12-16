package sless.ast

trait Rule extends Base {

  def isEmptyRule(rule: Rule): Boolean = rule match {
    case CommentRule(_, declarations, _) => declarations == Nil
  }

  def mapDeclarationsOfRule[A](rule: Rule, f: Declaration => A): Seq[A] = rule match {
    case CommentRule(_, declarations, _) => declarations.map(f)
  }

  def getRulesFrom(sheet: Css): Seq[Rule] = sheet match {
    case ACss(rules) => rules
  }

  def getSelectorFrom(rule: Rule): Selector = rule match {
    case CommentRule(s,_,_) => s
  }

  def transformDeclarationsOfRule(rule: RuleAST, f: DeclarationAST => DeclarationAST): RuleAST = rule match {
    case CommentRule(s, declarations, comment) => CommentRule(s, declarations.map(f), comment)
  }

  implicit class CssShorthand(c: Css) {
    def getRules: Seq[Rule] = getRulesFrom(c)
  }

  implicit class RuleShorthand(r: Rule) {
    def isEmpty: Boolean = isEmptyRule(r)
    def mapDeclarations[A](f: Declaration => A): Seq[A] = mapDeclarationsOfRule(r,f)
    def getSelector: Selector = getSelectorFrom(r)
  }

}
