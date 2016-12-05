package actors

import akka.actor._

object Message

class PrinterActor extends Actor {
    
  def receive = {
     case a:Any => 
         println("Print: " + a)
  }
  
}

object FireAndForgetActorMainApp extends App{
    
    val system = ActorSystem("SimpleActorSystem")
    val actor  = system.actorOf(Props[PrinterActor])
    
    val voidReturn = actor ! Message
    println("Actor says: " + voidReturn )
    
    system.terminate()
    
}