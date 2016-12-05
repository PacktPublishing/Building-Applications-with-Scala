package actors

object ActorHelper {
  
  import play.api.libs.concurrent.Execution.Implicits.defaultContext  
  import scala.concurrent.duration._
  import akka.pattern.ask
  import akka.actor.ActorRef
  import akka.util.Timeout
  import scala.concurrent.Future
  import scala.concurrent.Await
  
  def get(msg:Any,actor:ActorRef):String = {
    implicit val timeout = Timeout(5 seconds)
    val result = (actor ? msg).mapTo[String].map { result => result.toString }
    Await.result(result, 5.seconds)
  }
  
}