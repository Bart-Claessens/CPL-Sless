package sless.ast

import sless.dsl.CommentDSL

trait Comment extends CommentDSL with Base {
  override protected def commentRule(rule: RuleAST, str: String): RuleAST = CommentRule(rule,str)

  override protected def commentDeclaration(declaration: DeclarationAST, str: String): DeclarationAST
    = CommentDeclaration(declaration,str)


}
