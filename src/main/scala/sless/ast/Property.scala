package sless.ast

import sless.dsl.PropertyDSL

trait Property extends PropertyDSL with Base{
  override def prop(string: String): Property = AProperty(string)

  override protected def assign(p: Property, value: Value): Declaration = ADeclaration(p,value)

  def getPropertyFrom(d: Declaration): String = d match {
    case ADeclaration(AProperty(pvalue), _) => pvalue
    case CommentDeclaration(d, _) => getPropertyFrom(d)
  }

  def getValueOfPropertyFrom(d: Declaration, property: String): Option[String] = d match {
    case ADeclaration(AProperty(pvalue), AValue(value)) => if (pvalue.equals(property)) Some(value) else None
    case CommentDeclaration(d, _) => getValueOfPropertyFrom(d,property)
  }

  implicit class DeclarationShorthand(d: Declaration) {
    def getProperty: String = getPropertyFrom(d)
    def getValueOfProperty(property: String): Option[String] = getValueOfPropertyFrom(d,property)
    def hasProperty(property: String): Boolean = getPropertyFrom(d) == property
  }
}
