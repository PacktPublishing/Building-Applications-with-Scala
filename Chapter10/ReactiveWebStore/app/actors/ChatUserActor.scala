package actors

import akka.actor.ActorRef
import akka.actor.Actor
import akka.actor.ActorLogging
import akka.event.LoggingReceive
import akka.actor.ActorSystem
import akka.actor.Props

class ChatUserActor(room:ActorRef, out:ActorRef) extends Actor with ActorLogging {
  
  override def preStart() = {
    room ! JoinChatRoom
  }

  def receive = LoggingReceive {
    case ChatMessage(name, text) if sender == room =>
      val result:String = name + ":" + text
      out ! result
    case (text:String) =>
      room ! ChatMessage(text.split(":")(0), text.split(":")(1))
    case other =>
      log.error("issue - not expected: " + other)
  }
}

object ChatUserActor {
  def props(system:ActorSystem)(out:ActorRef) = Props(new ChatUserActor(ChatRoomActor(system), out))
}