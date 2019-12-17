package sless.ast

import sless.dsl.PropertyDSL

trait Property extends PropertyDSL with Base{
  override def prop(string: String): Property = AProperty(string)

  override protected def assign(p: Property, value: Value): Declaration = CommentDeclaration(p,value,null)

  def getPropertyFrom(d: Declaration): Property = d match {
    //case ADeclaration(AProperty(pvalue), _) => pvalue
    case CommentDeclaration(p, _, _) => p
  }

  def getValueOfPropertyFrom(d: Declaration, p: Property): Option[Value] = d match {
    //case ADeclaration(AProperty(pvalue), AValue(value)) => if (pvalue.equals(property)) Some(value) else None
    case CommentDeclaration(property, value, _) => if (property==p) Some(value) else None
  }

  implicit class DeclarationShorthand(d: Declaration) {
    def getProperty: Property = getPropertyFrom(d)
    def getValueOfProperty(p: Property): Option[Value] = getValueOfPropertyFrom(d,p)
//    def hasProperty(property: String): Boolean = getPropertyFrom(d) == p
    def hasProperty(p: Property): Boolean = getPropertyFrom(d) == p
  }
}
