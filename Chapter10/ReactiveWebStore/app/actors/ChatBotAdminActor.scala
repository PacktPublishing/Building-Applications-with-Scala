package actors

import akka.actor.ActorRef
import akka.actor.Actor
import akka.actor.ActorLogging
import akka.event.LoggingReceive
import akka.actor.ActorSystem
import akka.actor.Props
import scala.concurrent.duration._

class ChatBotAdminActor(system:ActorSystem) extends Actor with ActorLogging {
  
  import play.api.libs.concurrent.Execution.Implicits.defaultContext
  val room:ActorRef = ChatRoomActor(system)
  
  val cancellable = system.scheduler.schedule(0 seconds, 10 seconds, self , Tick)
  
  override def preStart() = {
    room ! JoinChatRoom
  }

  def receive = LoggingReceive {
    case ChatMessage(name, text) => Unit
    case (text:String)           => room ! ChatMessage(text.split(":")(0), text.split(":")(1))
    case Tick =>
        val response:String = "AdminBot:" + ActorHelper.get(GetStats, room)
        sender() ! response
    case other =>
      log.error("issue - not expected: " + other)
  }
}

object ChatBotAdminActor  {
  var bot:ActorRef = null
  
  def apply(system:ActorSystem) = {
    this.synchronized {
      if (bot==null) bot = system.actorOf(Props(new ChatBotAdminActor(system)))
      bot
    }
  }
}