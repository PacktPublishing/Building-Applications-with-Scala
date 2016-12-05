import concurrent.Future
import concurrent.ExecutionContext.Implicits.global

val f: Future[String] = Future { "Hello world!" }

println("Result: " + f.value.get.get)

println("Result: " + f)