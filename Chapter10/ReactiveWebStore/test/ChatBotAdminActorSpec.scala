
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
import actors.ChatBotAdminActor
import actors.Tick
import akka.testkit.ImplicitSender
import akka.testkit.DefaultTimeout
import org.scalatest.BeforeAndAfterAll
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import akka.testkit._

class ChatBotAdminActorSpec extends TestKit(ActorSystem("test")) 
  with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {
  
  "ChatBotAdminActor" should {
    
      "be able to create Bot Admin in the Chat Room and Tick" in  {
        
        val probe1 = new TestProbe(system)
        
        val actorRef = TestActorRef[ChatBotAdminActor](Props(new ChatBotAdminActor(system)))
        val botActor = actorRef.underlyingActor
        assert(botActor.context != null)
        awaitCond(botActor.room != null )
        awaitCond(botActor.cancellable != null )
    }
  }
  
}