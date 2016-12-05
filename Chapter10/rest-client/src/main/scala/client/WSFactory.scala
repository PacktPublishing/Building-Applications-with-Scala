package client

object WSFactory {
   
  import akka.actor.ActorSystem
  import akka.stream.ActorMaterializer
  
  def ws = {
    
      implicit val system = ActorSystem()
      implicit val materializer = ActorMaterializer()
    
      import com.typesafe.config.ConfigFactory
      import play.api._
      import play.api.libs.ws._
      import play.api.libs.ws.ahc.{AhcWSClient, AhcWSClientConfig}
      import play.api.libs.ws.ahc.AhcConfigBuilder
      import org.asynchttpclient.AsyncHttpClientConfig
      import java.io.File
      
      val configuration = Configuration.reference ++ Configuration(ConfigFactory.parseString(
        """
          |ws.followRedirects = true
        """.stripMargin))
    
      val parser = new WSConfigParser(configuration, play.api.Environment.simple(new File("/tmp/"), null))
      val config = new AhcWSClientConfig(wsClientConfig = parser.parse())
      val builder = new AhcConfigBuilder(config)
      val logging = new AsyncHttpClientConfig.AdditionalChannelInitializer() {
        override def initChannel(channel: io.netty.channel.Channel): Unit = {
          channel.pipeline.addFirst("log", new io.netty.handler.logging.LoggingHandler("debug"))
        }
      }
      val ahcBuilder = builder.configure()
      ahcBuilder.setHttpAdditionalChannelInitializer(logging)
      val ahcConfig = ahcBuilder.build()
      new AhcWSClient(ahcConfig)
  }
  
}