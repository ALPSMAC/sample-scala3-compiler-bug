package compiler.test

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import scala.io.StdIn
import play.api.libs.json.*

object Bootstrap extends PlayJsonSupport {

  def arrayOfABCs: JsArray = {
    import Model.given

    val abcs = (0 until 100).map{ i =>
      Model.ABC(
        a = scala.util.Random.alphanumeric.take(10).mkString(""),
        b = i,
        c = i.toDouble
      )
    }

    JsArray(abcs.map{ abc => Json.toJson(abc) })
  }

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.executionContext

    val route =
      path("hello") {
        get {
          complete[JsArray](StatusCodes.OK, arrayOfABCs)
        }
      }

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    println(s"Server now online. Please navigate to http://localhost:8080/hello\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}

object Model{
  given Format[ABC] = Json.format[ABC]

  case class ABC(
    a: String,
    b: Int,
    c: Double
  )
}
