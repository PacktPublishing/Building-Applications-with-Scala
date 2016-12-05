package mocks

import dao.ProductDao
import scala.concurrent.Future
import models.Product
import dao.IProductDao
import slick.lifted.TableQuery

class ProductMockedDao extends IProductDao {
  
  val dao:GenericMockedDao[Product] = new GenericMockedDao[Product]()
  
  override def findAll(): Future[Seq[Product]] = {
     dao.findAll()    
  }
  
  override def findById(id:Long): Future[Option[Product]] = {
     dao.findById(id)
  }
  
  override def remove(id:Long): Future[Int] = {  
     dao.remove(id)
  }
  
  override def insert(p:Product): Future[Unit] = {
     dao.insert(p)
  }
  
  override def update(p2:Product): Future[Unit] = {
    dao.update(p2)
  } 
  
  override def toTable:TableQuery[_] = {
    null
  }
  
}