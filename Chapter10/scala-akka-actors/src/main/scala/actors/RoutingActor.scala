package actors

import akka.actor._
import akka.routing.RoundRobinPool

class ActorUpperCasePrinter extends Actor {
  def receive = {
    case s:Any =>
      println("Msg: " + s.toString().toUpperCase() + " - " + self.path)
  }
}

object RoutingActorApp extends App {
    
    val system = ActorSystem("SimpleActorSystem")
    val actor:ActorRef = system.actorOf( RoundRobinPool(5).props(Props[ActorUpperCasePrinter]),name = "actor")
    
    try{
       actor ! "works 1"
       actor ! "works 2"
       actor ! "works 3"
       actor ! "works 4"
       actor ! "works 5"
       actor ! "works 6"
       
    }catch{
       case e:RuntimeException => println(e.getMessage()) 
    }  
    
    system.terminate()
    
}