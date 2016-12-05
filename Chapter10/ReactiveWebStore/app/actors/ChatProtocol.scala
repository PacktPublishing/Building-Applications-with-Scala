package actors

case class ChatMessage(name:String,text: String)
case class Stats(users:Set[String])

object JoinChatRoom
object Tick
object GetStats
