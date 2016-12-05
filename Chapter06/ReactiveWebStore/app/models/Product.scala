package models

case class Product 
 ( var id:Option[Long],
   var name:String,
   var details:String,
   var price:BigDecimal ) 
extends BaseModel{
  override def toString:String = {
    "Product { id: " + id.getOrElse(0) + ",name: " + name + ", details: "+ details + ", price: " + price  + "}" 
  }
  override def getId:Option[Long] = id
  override def setId(id:Option[Long]):Unit = this.id = id
}

object ProductDef{
  def toTable:String = "Product"
}