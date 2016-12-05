import java.time.Clock
import com.google.inject.AbstractModule
import javax.inject.Singleton
import services.ApplicationTimer
import services.IImageService
import services.IPriceSerice
import services.IProductService
import services.IReviewService
import services.IRndService
import services.ImageService
import services.PriceService
import services.ProductService
import services.ReviewService
import services.RndService
import dao.IProductDao
import dao.ProductDao
import dao.ImageDao
import dao.IImageDao
import dao.ReviewDao
import dao.IReviewDao

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.

 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
class Module extends AbstractModule {

  override def configure() = {
    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
    // Ask Guice to create an instance of ApplicationTimer when the
    // application starts.
    bind(classOf[ApplicationTimer]).asEagerSingleton()
    
    bind(classOf[IProductService]).to(classOf[ProductService]).asEagerSingleton()
    bind(classOf[IReviewService]).to(classOf[ReviewService]).asEagerSingleton()
    bind(classOf[IImageService]).to(classOf[ImageService]).asEagerSingleton()
    
    bind(classOf[IPriceSerice]).to(classOf[PriceService]).asEagerSingleton()
    bind(classOf[IRndService]).to(classOf[RndService]).asEagerSingleton()
    
    bind(classOf[IProductDao]).to(classOf[ProductDao]).asEagerSingleton()
    bind(classOf[IImageDao]).to(classOf[ImageDao]).asEagerSingleton()
    bind(classOf[IReviewDao]).to(classOf[ReviewDao]).asEagerSingleton()
    
  }

}
