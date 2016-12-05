package actors

import akka.actor.Props
import akka.actor.Terminated
import akka.actor.ActorLogging
import akka.event.LoggingReceive
import akka.actor.Actor
import akka.actor.ActorRef
import play.libs.Akka
import akka.actor.ActorSystem

class ChatRoomActor extends Actor with ActorLogging {

  var users = Set[ActorRef]()

  def receive = LoggingReceive {
    case msg: ChatMessage =>
      users foreach { _ ! msg }
    case JoinChatRoom =>
      users += sender
      context watch sender
    case GetStats =>
      val stats:String = "online users[" + users.size + "] - users[" + users.map( a => a.hashCode().toString() ).mkString("|") + "]"
      sender ! stats
    case Terminated(user) =>
      users -= user
  }
}

object ChatRoomActor {
  var room:ActorRef = null
  
  def apply(system:ActorSystem) = {
    this.synchronized {
      if (room==null) room = system.actorOf(Props[ChatRoomActor])
      room
    }
  }
  
}