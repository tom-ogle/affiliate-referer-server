package controllers

import java.net.URL

import play.api.mvc._

/**
  * The main controller for the app.
  */
class RefererController extends Controller {
  val RefererHeaderKey = "Referer"

  def index = Action { request =>
    val headers: Headers = request.headers
    val referer = headers.get(RefererHeaderKey)
    val result = referer.map { referer =>
      s"$RefererHeaderKey header value: $referer"
    }.getOrElse(s"No $RefererHeaderKey header value")
    Ok(result)
  }

  def filter(url: URL) = TODO

  def meta = Action { request =>
    val headers = request.headers.toMap.map {
      case (headerName, values: Seq[String]) => s"$headerName: ${values.mkString(", ")}"
    }.mkString("\n")
    // TODO: Format this in a view
    Ok(s"Headers:\n\n$headers")
  }
}
