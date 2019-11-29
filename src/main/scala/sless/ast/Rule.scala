package sless.ast

trait Rule extends Base {

  def isEmptyRule(rule: Rule): Boolean = rule match {
    case ARule(_, declarations) => declarations == Nil
    case CommentRule(r, _) => isEmptyRule(r)
  }

  def mapDeclarationsOfRule[A](rule: Rule, f: Declaration => A): Seq[A] = rule match {
    case ARule(s, declarations) => declarations.map(f)
    case CommentRule(r, _) => mapDeclarationsOfRule(r,f)
  }

  def getRulesFrom(sheet: Css): Seq[Rule] = sheet match {
    case ACss(rules) => rules
  }


//  def transformDeclarationsOfRule(rule: RuleAST, f: DeclarationAST => DeclarationAST): RuleAST = rule match {
//    case ARule(s, declarations) => ARule(s, declarations.map(f))
//    case CommentRule(r, comment) => CommentRule(transformDeclarationsOfRule(r,f),comment)
//  }

  implicit class CssShorthand(c: Css) {
    def getRules: Seq[Rule] = getRulesFrom(c)
  }

  implicit class RuleShorthand(r: Rule) {
    def isEmpty: Boolean = isEmptyRule(r)
    def mapDeclarations[A](f: Declaration => A): Seq[A] = mapDeclarationsOfRule(r,f)
  }

}
