package controllers


import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.Messages
import play.api.i18n.I18nSupport
import play.api.i18n.MessagesApi
import services.IProductService
import play.Application
import services.IReviewService
import models.Review
import utils.Awaits

@Singleton
class ReviewController @Inject() 
  (val messagesApi:MessagesApi,
   val productService:IProductService,
   val service:IReviewService) 
extends Controller with I18nSupport {
  
    val reviewForm:Form[Review] = Form(
    mapping(
      "id"        -> optional(longNumber),  
      "productId" -> optional(longNumber),
      "author"    -> nonEmptyText,
      "comment"   -> nonEmptyText
    )(models.Review.apply)(models.Review.unapply))

  def index = Action { implicit request =>
    val reviews = Awaits.get(5,service.findAll()).getOrElse(Seq())
    Logger.info("index called. Reviews: " + reviews)
    Ok(views.html.review_index(reviews))
  }

  def blank = Action { implicit request =>
    Logger.info("blank called. ")
    Ok(views.html.review_details(None, reviewForm,productService.findAllProducts))
  }

  def details(id: Long) = Action { implicit request =>
    Logger.info("details called. id: " + id)
    val review = Awaits.get(5,service.findById(id)).get
    Ok(views.html.review_details(Some(id), reviewForm.fill(review),productService.findAllProducts))
  }

  def insert()= Action { implicit request =>
    Logger.info("insert called.")
    reviewForm.bindFromRequest.fold(
      form => {
        BadRequest(views.html.review_details(None, form,productService.findAllProducts))
      },
      review => {
         if (review.productId==null || review.productId.getOrElse(0)==0) {
            Redirect(routes.ReviewController.blank).flashing("error" -> "Product ID Cannot be Null!")
        }else {
            Logger.info("Review: " + review)
            if (review.productId==null || review.productId.getOrElse(0)==0) throw new IllegalArgumentException("Product Id Cannot Be Null")
            service.insert(review)
            Redirect(routes.ReviewController.index).flashing("success" -> Messages("success.insert", "new Review"))        
        }
      })
  }

  def update(id: Long) = Action { implicit request =>
    Logger.info("updated called. id: " + id)
    reviewForm.bindFromRequest.fold(
      form => {
        Ok(views.html.review_details(Some(id), form,productService.findAllProducts)).flashing("error" -> "Fix the errors!")
      },
      review => {
        service.update(id,review)
        Redirect(routes.ReviewController.index).flashing("success" -> Messages("success.update", review.productId))
      })
  }

  def remove(id: Long)= Action {

      import play.api.libs.concurrent.Execution.Implicits.defaultContext
    
      val result =  Awaits.get(5,service.findById(id))
      
      result.map { review =>
        service.remove(id)
        Redirect(routes.ReviewController.index).flashing("success" -> Messages("success.delete", review.productId))
      }.getOrElse(NotFound)

  }
 
  
}