package binders

import java.net.URL

import play.api.Logger
import play.api.mvc.QueryStringBindable

import scala.util.Try

/**
  * Project specific Binders
  */
object ProjectBinders {

  private val logger = Logger(this.getClass)

  /**
    * Binds java.net.URL objects in URL query strings.
    */
  // TODO: THis can probably be simplified by implementing Parsing
  implicit def urlQueryStringBindable: QueryStringBindable[URL] = new QueryStringBindable[URL] {
    private val onSuccess: (URL) => Either[String, URL] = url => Right(url)
    private val onFailure: PartialFunction[Throwable, Either[String, URL]] = {
      case t: Throwable =>
        val message = s"Could not bind Query String Parameter URL: ${t.getMessage}"
        logger.error(message, t)
        Left(message)
    }

    override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, URL]] = {
      val values = params.get(key).withFilter(_ != null)
      val allResults = values.map { seq =>
        seq.map { value =>
          Try(new URL(value)).map(onSuccess).recover(onFailure).get
        }
      }
      allResults.flatMap(_.headOption)
    }

    override def unbind(key: String, value: URL): String = s"$key=${value.toString}"
  }

}
