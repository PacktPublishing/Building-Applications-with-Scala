package services

import models.Product
import javax.inject._
import dao.ProductDao
import scala.concurrent.Future
import utils.Awaits
import dao.IProductDao

trait IProductService extends BaseService[Product]{
  def insert(product:Product):Future[Unit]
  def update(id:Long,product:Product):Future[Unit]
  def remove(id:Long):Future[Int]
  def findById(id:Long):Future[Option[Product]]
  def findAll():Future[Option[Seq[Product]]]
  def findAllProducts():Seq[(String,String)]
}

@Singleton
class ProductService @Inject() (dao:IProductDao)  extends IProductService{
  
  import play.api.libs.concurrent.Execution.Implicits.defaultContext
  
  def insert(product:Product):Future[Unit] = {
     dao.insert(product);
  }
  
  def update(id:Long,product:Product):Future[Unit] = {
    product.id =  Option(id.toInt)
    dao.update(product)
  }
  
  def remove(id:Long):Future[Int] = {
    dao.remove(id)
  }
  
  def findById(id:Long):Future[Option[Product]] = {
     dao.findById(id)
  }
  
  def findAll():Future[Option[Seq[Product]]] = {
    dao.findAll().map { x => Option(x) }
  }
  
  private def validateId(id:Long):Unit = {
     val future = findById(id)
     val entry  = Awaits.get(5, future)  
     if (entry==null || entry.equals(None)) throw new RuntimeException("Could not find Product: " + id)
  }
  
  def findAllProducts():Seq[(String,String)] = {
    val future = this.findAll()
    val result = Awaits.get(5, future)

    val products:Seq[(String,String)] = result  
          .getOrElse(Seq(Product(Some(0),"","",0)))
          .toSeq
          .map { product => (product.id.get.toString,product.name) }
    
    return products
  }
  
}