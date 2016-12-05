package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import services.IProductService
import models.ProductsJson
import models.ReviewsJson
import models.ImagesJson
import utils.Awaits
import services.IReviewService
import services.IImageService
import backpresurre.LeakyBucket
import io.swagger.annotations.ApiResponses
import io.swagger.annotations.ApiResponse
import io.swagger.annotations._

@Singleton
@Api(value = "/REST/api", description = "REST operations on Products, Images and Reviews. ")
class RestAPIController @Inject() 
   (val productService:IProductService,
    val reviewService:IReviewService,
    val imageService:IImageService) extends Controller {
   
   import play.api.libs.concurrent.Execution.Implicits.defaultContext
   
   @ApiOperation(
      nickname = "listAllProducts", 
      value = "Find All Products", 
      notes = "Returns all Products", 
      response = classOf[models.Product], 
      httpMethod = "GET"
   )
   @ApiResponses(Array(
      new ApiResponse(code = 500, message = "Internal Server Error"),
      new ApiResponse(code = 200, message = "JSON response with data")
   ))
   def listAllProducts = Action {
      val future = productService.findAll()
      val products =  Awaits.get(5,future)
      val json =  ProductsJson.toJson(products)
      Ok(json)
   }

   @ApiOperation(
      nickname = "listAllReviews", 
      value = "Find All Reviews", 
      notes = "Returns all Reviews", 
      response = classOf[models.Review], 
      httpMethod = "GET"
   )
   @ApiResponses(Array(
      new ApiResponse(code = 500, message = "Internal Server Error"),
      new ApiResponse(code = 200, message = "JSON response with data")
   ))
   def listAllReviews = Action {
      val future  = reviewService.findAll()
      val reviews =  Awaits.get(5,future)
      val json =  ReviewsJson.toJson(reviews)
      Ok(json)
   }
   
   import scala.concurrent.duration._
   var bucket = new LeakyBucket(5, 60 seconds)
   
   def processImages = {
       val future  =  imageService.findAll()
       val images  =  Awaits.get(5,future)
       val json    =  ImagesJson.toJson(images)
       json
   }
     
   def processFailure = {
     Json.toJson("Too Many Requests - Try Again later... ")
   }  

   @ApiOperation(
      nickname = "listAllImages", 
      value = "Find All Images", 
      notes = "Returns all Images - There is thottling of 5 reqs/sec", 
      response = classOf[models.Image], 
      httpMethod = "GET"
   )
   @ApiResponses(Array(
      new ApiResponse(code = 500, message = "Internal Server Error"),
      new ApiResponse(code = 200, message = "JSON response with data")
   ))
   def listAllImages = Action {
     bucket.dropToBucket() match {
       case true  => Ok(processImages) 
       case false => InternalServerError(processFailure.toString())
     }
   }
  
}