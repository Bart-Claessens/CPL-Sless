package sless.ast

import sless.dsl.PropertyDSL

trait Property extends PropertyDSL with Base{
  override def prop(string: String): Property = AProperty(string)

  override protected def assign(p: Property, value: Value): Declaration = CommentDeclaration(p,value,null)

  def getPropertyFrom(d: Declaration): Property = d match {
    case CommentDeclaration(p, _, _) => p
  }

  def getValueOfPropertyFrom(d: Declaration, p: Property): Option[Value] = d match {
    case CommentDeclaration(property, value, _) => if (property==p) Some(value) else None
  }

  implicit class DeclarationShorthand(d: Declaration) {
    def getProperty: Property = getPropertyFrom(d)
    def getValueOfProperty(p: Property): Option[Value] = getValueOfPropertyFrom(d,p)
    def hasProperty(p: Property): Boolean = getPropertyFrom(d) == p
  }
}
