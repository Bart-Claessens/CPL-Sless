package sless.ast

import sless.dsl.PropertyDSL

trait Property extends PropertyDSL with Base{
  override def prop(string: String): Property = AProperty(string)

  override protected def assign(p: Property, value: Value): Declaration = CommentDeclaration(p,value,null)

  def getPropertyFrom(d: Declaration): String = d match {
    //case ADeclaration(AProperty(pvalue), _) => pvalue
    case CommentDeclaration(AProperty(pvalue), _, _) => pvalue
  }

  def getValueOfPropertyFrom(d: Declaration, property: String): Option[String] = d match {
    //case ADeclaration(AProperty(pvalue), AValue(value)) => if (pvalue.equals(property)) Some(value) else None
    case CommentDeclaration(AProperty(pvalue), AValue(value), _) => if (pvalue.equals(property)) Some(value) else None
    case _ => None
  }

  implicit class DeclarationShorthand(d: Declaration) {
    def getProperty: String = getPropertyFrom(d)
    def getValueOfProperty(property: String): Option[String] = getValueOfPropertyFrom(d,property)
    def hasProperty(property: String): Boolean = getPropertyFrom(d) == property
  }
}
