package utils

import slick.jdbc.JdbcBackend.Database
import slick.driver.MySQLDriver.api._
import com.typesafe.config.ConfigFactory
import scala.concurrent.Await
import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.concurrent.Future
import slick.backend.DatabasePublisher
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.jdbc.GetResult

object DBCleaner {
  
  val pool = Executors.newCachedThreadPool()
  implicit val ec = ExecutionContext.fromExecutorService(pool)
  
  def cleanUp():Unit = {
      Class.forName("com.mysql.cj.jdbc.Driver")
      val db = Database.forURL("jdbc:mysql://127.0.0.1:3306/RWS_DB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
          "root", "")
          
      try{
         Await.result(
             db.run(
                 DBIO.seq(
                   sqlu""" DELETE FROM Product;  """,
                   sqlu""" DELETE FROM Image;    """,
                   sqlu""" DELETE FROM Review;   """,
                   sqlu""" ALTER TABLE Product AUTO_INCREMENT = 1 """,
                   sqlu""" ALTER TABLE Image   AUTO_INCREMENT = 1 """,
                   sqlu""" ALTER TABLE Review  AUTO_INCREMENT = 1 """
                 )
             )
         ,20 seconds)
      }catch{
         case e:Exception => Unit
      }
  }
  
}