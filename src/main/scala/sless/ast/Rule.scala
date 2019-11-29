package sless.ast

trait Rule extends Base {

  def isEmptyRule(rule: RuleAST): Boolean = rule match {
    case ARule(_, declarations) => declarations == Nil
    case CommentRule(r, _) => isEmptyRule(r)
  }

  def mapDeclarationsOfRule[A](rule: RuleAST, f: DeclarationAST => A): Seq[A] = rule match {
    case ARule(s, declarations) => declarations.map(f)
    case CommentRule(r, comment) => mapDeclarationsOfRule(r,f)
  }

//  def transformDeclarationsOfRule(rule: RuleAST, f: DeclarationAST => DeclarationAST): RuleAST = rule match {
//    case ARule(s, declarations) => ARule(s, declarations.map(f))
//    case CommentRule(r, comment) => CommentRule(transformDeclarationsOfRule(r,f),comment)
//  }

  implicit class RuleShorthand(r: Rule) {
    def isEmpty: Boolean = isEmptyRule(r)
    def mapDeclarations[A](f: DeclarationAST => A): Seq[A] = mapDeclarationsOfRule(r,f)
  }

}
