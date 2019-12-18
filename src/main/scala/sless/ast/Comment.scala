package sless.ast

import sless.dsl.CommentDSL

trait Comment extends CommentDSL with Base {
  protected def commentRule(rule: Rule, str: String): Rule =
    rule match {
      case CommentRule(s, declarations, _) => CommentRule(s, declarations, str)
    }

  override protected def commentDeclaration(declaration: Declaration, str: String): Declaration =
    declaration match {
      case CommentDeclaration(p,value,_) => CommentDeclaration(p,value,str)
    }


}
