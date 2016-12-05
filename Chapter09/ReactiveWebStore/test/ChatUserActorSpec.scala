
import org.scalatestplus.play.PlaySpec
import actors.ChatMessage
import actors.ChatUserActor
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import akka.testkit.TestKit
import akka.testkit.TestProbe
import akka.actor.Props
import org.scalatestplus.play._
import scala.concurrent.duration.DurationInt

class OutActor extends Actor {
    def receive = {
        case a:Any => Unit
    }
}

class ChatUserActorSpec  extends PlaySpec  {
  
  class Actors extends TestKit(ActorSystem("test")) 
  
  "ChatUserActor" should {
    
      "joins the chat room and send a message" in new Actors {
        
        val probe1 = new TestProbe(system)
        val actorOutRef = TestActorRef[OutActor](Props[OutActor])
        
        val actorRef = TestActorRef[ChatUserActor](ChatUserActor.props(system)(actorOutRef))
        val userActor = actorRef.underlyingActor
        assert(userActor.context != null)
  
        val msg = "testUser:test msg"
        probe1.send(actorRef,msg) 
        actorRef.receive(msg)
        receiveOne(2000 millis)            
    }
  }
  
}