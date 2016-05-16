package binders

import java.net.URL

import org.scalatest.{FlatSpec, Matchers}

class ProjectBindersTest extends FlatSpec with Matchers {

  behavior of "urlQueryStringBindable unbind"

  trait UnbindFixture {
    def unbind = ProjectBinders.urlQueryStringBindable.unbind _
  }

  it should "unbind a key 'url' and an URL to a query String" in
    new UnbindFixture {
      val expected = "url=http://www.google.com"
      val result = unbind("url", new URL("http://www.google.com"))
      result should be(expected)
  }

  it should "unbind a key 'anotherkey' and an URL to a query String" in
    new UnbindFixture {
      val expected = "anotherkey=http://www.yahoo.com"
      val result = unbind("anotherkey", new URL("http://www.yahoo.com"))
      result should be(expected)
  }

  behavior of "urlQueryStringBindable bind"

  trait BindFixture {
    val params = Map("key-cannot-be-converted-to-URL" -> Seq(""),
      "key-one-url" -> Seq("http://www.google.com"),
      "key-empty-seq" -> Seq(),
      "key-null" -> null,
      "key-two-urls" -> Seq("http://www.yahoo.com", "http://www.google.com"),
      "key-one-url-one-cannot-be-converted" -> Seq("http://www.yahoo.com", "cannot-be-converted")
    )

    def bind = ProjectBinders.urlQueryStringBindable.bind _
  }

  it should "not bind a key that is not present in the query String" in
    new BindFixture {
      val result = bind("not present", params)
      result shouldBe 'empty
    }

  it should "not bind a key that is present in the query String with no values" in
    new BindFixture {
      val result = bind("key-empty-seq", params)
      result shouldBe 'empty
    }

  it should "not bind a key that is present in the query String with null value" in
    new BindFixture {
      val result = bind("key-null", params)
      result shouldBe 'empty
    }

  it should "give an error for a key that is present in the query String but cannot be converted to a URL" in
    new BindFixture {
      val result = bind("key-cannot-be-converted-to-URL", params)
      result shouldBe 'defined
      result.get shouldBe 'left
    }

  it should "bind a key that is present with one value" in
    new BindFixture {
      val expectedUrl = new URL("http://www.google.com")
      val result = bind("key-one-url", params)
      result shouldBe 'defined
      result.get shouldBe 'right
      result.get.right.get should equal(expectedUrl)
    }

  it should "bind the first value for a key that is present with multiple bindable values" in
    new BindFixture {
      val expectedUrl = new URL("http://www.yahoo.com")
      val result = bind("key-two-urls", params)
      result shouldBe 'defined
      result.get shouldBe 'right
      result.get.right.get should equal(expectedUrl)
    }

  it should "bind the first value for a key that is present with first value bindable and other snot bindable" in
    new BindFixture {
      val expectedUrl = new URL("http://www.yahoo.com")
      val result = bind("key-one-url-one-cannot-be-converted", params)
      result shouldBe 'defined
      result.get shouldBe 'right
      result.get.right.get should equal(expectedUrl)
    }
}
