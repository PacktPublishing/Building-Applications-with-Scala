package actors

import akka.actor._

object HelloMessage

class HelloWorldActor extends Actor {
    
  def receive = {
     case HelloMessage => sender() ! "Hello World"
     case a:Any => sender() ! "I don't know: " + a + " - Sorry!"
  }
  
}

object SimpleActorMainApp extends App{
    
    val system = ActorSystem("SimpleActorSystem")
    val actor  = system.actorOf(Props[HelloWorldActor])
    
    import scala.concurrent.duration._
    import akka.util.Timeout
    import akka.pattern.ask
    import scala.concurrent.Await
    implicit val timeout = Timeout(20 seconds) 
    
    val future = actor ? HelloMessage
    val result = Await.result(future, timeout.duration).asInstanceOf[String]
    println("Actor says: " + result )
    
    val future2 = actor ? "Cobol"
    val result2 = Await.result(future2, timeout.duration).asInstanceOf[String]
    println("Actor says: " + result2 )
    
    system.terminate()
    
}