package controllers

import akka.actor.ActorSystem
import akka.stream.Materializer
import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.streams._
import actors.ChatUserActor
import actors.ChatBotAdminActor

@Singleton
class ChatController @Inject() (implicit val system: ActorSystem, materializer: Materializer) 
extends Controller {
  
   import play.api.libs.concurrent.Execution.Implicits.defaultContext
   
   ChatBotAdminActor(system)
   
   def index_socket = Action { request =>
      Ok(views.html.chat_index()(Flash(Map())))
   }
   
   def ws = WebSocket.accept[String, String] { request =>
      ActorFlow.actorRef(out => ChatUserActor.props(system)(out))
   }
  
}