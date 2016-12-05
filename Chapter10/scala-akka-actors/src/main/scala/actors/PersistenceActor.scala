package actors

import akka.actor._
import akka.persistence._
import scala.concurrent.duration._

class PersistenceActor extends PersistentActor{
      
  override def persistenceId = "sample-id-1"
  
  var state:String = "myState"
  var count = 0
  
  def receiveCommand: Receive = {
     case payload: String =>
        println(s"PersistenceActor received ${payload} (nr = ${count})")
        persist(payload + count) { evt =>
          count += 1
        }
  }
  
  def receiveRecover: Receive = {
      case _: String =>
        println("recover...")
        count += 1
  }
    
}

object PersistentViewsApp extends App {
     
    val system = ActorSystem("SimpleActorSystem")
    val persistentActor = system.actorOf(Props(classOf[PersistenceActor]))
    
    import system.dispatcher    
    system.scheduler.schedule(Duration.Zero, 2.seconds, persistentActor, "scheduled")
    
}