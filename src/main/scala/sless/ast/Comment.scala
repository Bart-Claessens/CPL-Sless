package sless.ast

import sless.dsl.CommentDSL

trait Comment extends CommentDSL with Base {
  protected def commentRule(rule: Rule, str: String): Rule =
    rule match {
      //case ARule(s, declarations) => CommentRule(s, declarations, str)
      case CommentRule(s, declarations, _) => CommentRule(s, declarations, str)
    }

  override protected def commentDeclaration(declaration: Declaration, str: String): Declaration =
    declaration match {
      //case ADeclaration(p,value)
      case CommentDeclaration(p,value,_) => CommentDeclaration(p,value,str)
    }


}
