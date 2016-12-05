package mocks

import scala.concurrent.Future
import dao.ReviewDao
import models.Review
import slick.lifted.TableQuery
import dao.IReviewDao

class ReviewMockedDao extends IReviewDao {
  
  val dao:GenericMockedDao[Review] = new GenericMockedDao[Review]()
  
  override def findAll(): Future[Seq[Review]] = {
     dao.findAll()    
  }
  
  override def findById(id:Long): Future[Option[Review]] = {
     dao.findById(id)
  }
  
  override def remove(id:Long): Future[Int] = {  
     dao.remove(id)
  }
  
  override def insert(p:Review): Future[Unit] = {
     dao.insert(p)
  }
  
  override def update(p2:Review): Future[Unit] = {
    dao.update(p2)
  } 
  
  override def toTable:TableQuery[_] = {
    null
  }
  
}