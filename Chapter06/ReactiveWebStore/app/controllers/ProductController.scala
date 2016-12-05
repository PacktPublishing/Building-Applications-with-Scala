package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data._
import models.Product
import play.api.i18n.Messages
import play.api.i18n.I18nSupport
import play.api.i18n.MessagesApi
import services.IProductService
import play.Application
import utils.Awaits

@Singleton
class ProductController @Inject() (val messagesApi:MessagesApi,val service:IProductService) extends Controller with I18nSupport {
   
    val productForm: Form[Product] = Form(
    mapping(
      "id"        -> optional(longNumber),  
      "name"      -> nonEmptyText,
      "details"   -> text,
      "price"     -> bigDecimal
    )(models.Product.apply)(models.Product.unapply))

  def index = Action { implicit request =>
    val products = Awaits.get(5,service.findAll()).getOrElse(Seq())
    Logger.info("index called. Products: " + products)
    Ok(views.html.product_index(products))
  }

  def blank = Action { implicit request =>
    Logger.info("blank called. ")
    Ok(views.html.product_details(None, productForm))
  }

  def details(id: Long) = Action { implicit request =>
    Logger.info("details called. id: " + id)
    val product = Awaits.get(5,service.findById(id)).get
    Ok(views.html.product_details(Some(id), productForm.fill(product)))
  }

  def insert()= Action { implicit request =>
    Logger.info("insert called.")
    productForm.bindFromRequest.fold(
      form => {
        BadRequest(views.html.product_details(None, form))
      },
      product => {
        service.insert(product)
        Redirect(routes.ProductController.index).flashing("success" -> Messages("success.insert", "new product created"))
      })
  }

  def update(id: Long) = Action { implicit request =>
    Logger.info("updated called. id: " + id)
    productForm.bindFromRequest.fold(
      form => {
        Ok(views.html.product_details(Some(id), form)).flashing("error" -> "Fix the errors!")
      },
      product => {
        service.update(id,product)
        Redirect(routes.ProductController.index).flashing("success" -> Messages("success.update", product.name))
      })
  }

  def remove(id: Long)= Action {
    
      import play.api.libs.concurrent.Execution.Implicits.defaultContext
    
      val result =  Awaits.get(5,service.findById(id))
      
      result.map { product =>
        service.remove(id)
        Redirect(routes.ProductController.index).flashing("success" -> Messages("success.delete", product.name))
      }.getOrElse(NotFound)
  }
  
}