

import scala.concurrent.duration.DurationInt
import org.junit.runner.RunWith
import akka.actor.Props
import akka.testkit.TestActorRef
import akka.testkit.TestProbe
import akka.actor.PoisonPill
import akka.testkit.TestKit
import akka.testkit.ImplicitSender
import akka.actor.ActorSystem
import org.scalatestplus.play._
import actors.JoinChatRoom
import actors.ChatRoomActor
import actors.GetStats
import actors.ChatMessage

class ChatRoomActorSpec extends PlaySpec  {
  
  class Actors extends TestKit(ActorSystem("test")) 
  
  "ChatRoomActor" should {
    
    "accept joins the chat rooms" in new Actors {
      
        val probe1 = new TestProbe(system)
        val probe2 = new TestProbe(system)
      
        val actorRef = TestActorRef[ChatRoomActor](Props[ChatRoomActor])
        val roomActor = actorRef.underlyingActor
  
        assert(roomActor.users.size == 0)
  
        probe1.send(actorRef, JoinChatRoom)
        probe2.send(actorRef, JoinChatRoom)
  
        awaitCond(roomActor.users.size == 2, 100 millis)
  
        assert(roomActor.users.contains(probe1.ref))
        assert(roomActor.users.contains(probe2.ref))
    }
    
    "get stats from the chat room" in new Actors {
      
        val probe1 = new TestProbe(system)
        val actorRef = TestActorRef[ChatRoomActor](Props[ChatRoomActor])
        val roomActor = actorRef.underlyingActor
  
        assert(roomActor.users.size == 0)
        probe1.send(actorRef, JoinChatRoom)
        awaitCond(roomActor.users.size == 1, 100 millis)
        assert(roomActor.users.contains(probe1.ref))
        
        probe1.send(actorRef, GetStats)
        receiveOne(2000 millis)
    }
    
    "and broadcast messages" in new Actors {
      
        val probe1 = new TestProbe(system)
        val probe2 = new TestProbe(system)
        
        val actorRef = TestActorRef[ChatRoomActor](Props[ChatRoomActor])
        val roomActor = actorRef.underlyingActor
  
        probe1.send(actorRef, JoinChatRoom)
        probe2.send(actorRef, JoinChatRoom)
        awaitCond(roomActor.users.size == 2, 100 millis)
  
        val msg = ChatMessage("sender", "test message")
        actorRef.receive(msg)
        probe1.expectMsg(msg)
        probe2.expectMsg(msg)
        
    }
    
    "and track users ref and counts" in new Actors {
        val probe1 = new TestProbe(system)
        val probe2 = new TestProbe(system)

        val actorRef = TestActorRef[ChatRoomActor](Props[ChatRoomActor])
        val roomActor = actorRef.underlyingActor
  
        probe1.send(actorRef, JoinChatRoom)
        probe2.send(actorRef, JoinChatRoom)
        awaitCond(roomActor.users.size == 2, 100 millis)
        
        probe2.ref ! PoisonPill
        awaitCond(roomActor.users.size == 1, 100 millis)
    }
    
  }
  
}