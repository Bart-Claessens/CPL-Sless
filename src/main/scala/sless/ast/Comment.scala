package sless.ast

import sless.dsl.CommentDSL

trait Comment extends CommentDSL with Base {
  protected def commentRule(rule: Rule, str: String): Rule = CommentRule(rule,str)

  override protected def commentDeclaration(declaration: Declaration, str: String): Declaration
    = CommentDeclaration(declaration,str)


}
