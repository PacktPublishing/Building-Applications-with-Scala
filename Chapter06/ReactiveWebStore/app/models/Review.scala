package models

case class Review
 (var id:Option[Long],
  var productId:Option[Long],
  var author:String,
  var comment:String)
extends BaseModel{
  override def toString:String = {
    "Review { id: " + id + " ,productId: " + productId.getOrElse(0) + ",author: " + author + ",comment: " + comment +  " }" 
  }
  override def getId:Option[Long] = id
  override def setId(id:Option[Long]):Unit = this.id = id
}

object ReviewDef{
  def toTable:String = "Review"
}