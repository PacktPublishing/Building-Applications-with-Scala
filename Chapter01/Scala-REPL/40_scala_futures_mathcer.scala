import concurrent.Future
import concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

def createFuture():Future[Int] = {
  Future { 
 	val r = scala.util.Random
	if (r.nextInt(100)%2==0) 0 else throw new RuntimeException("ODD numbers are not good here :( ")
  }
}

def evaluateFuture(f:Future[_]) {
	f.onComplete {
	    case Success(i) => println(s"A Success $i ")
	    case Failure(e) => println(s"Something went wrong. Ex: ${e.getMessage}")
    }
}

evaluateFuture(createFuture)
evaluateFuture(createFuture)
evaluateFuture(createFuture)

evaluateFuture(createFuture)
evaluateFuture(createFuture)
evaluateFuture(createFuture)