package mocks

import models.BaseModel

class GenericMockedDao[T <: BaseModel] {
  
  import java.util.concurrent.atomic.AtomicLong
  import scala.collection.mutable.HashMap
  
  import scala.concurrent._
  import play.api.libs.concurrent.Execution.Implicits.defaultContext
  
  var inMemoryDB = new HashMap[Long,T]
  var idCounter = new AtomicLong(0)
  
  def findAll(): Future[Seq[T]] = {
     Future {
        if (inMemoryDB.values.toList == null || inMemoryDB.values.toList.size==0) Seq()
        inMemoryDB.values.toSeq
     }
  }
  
  def findById(id:Long): Future[Option[T]] = {
     Future {
        inMemoryDB.get(id)
     }
  }
  
  def remove(id:Long): Future[Int] = {  
    Future {
        validateId(id)
        inMemoryDB.remove(id)
        1
    }
  }
  
  def insert(t:T): Future[Unit] = {
     Future {
        val id = idCounter.incrementAndGet();
        t.setId(Some(id))
        inMemoryDB.put(id, t)
        Unit
     }
  }
  
  def update(t:T): Future[Unit] = {
     Future {
         validateId(t.getId.get)
         inMemoryDB.put(t.getId.get, t)
         Unit
     }
  } 
  
  private def validateId(id:Long):Unit = {
     val entry = inMemoryDB.get(id)
     if (entry==null || entry.equals(None)) throw new RuntimeException("Could not find Product: " + id)
  }
 
  
}