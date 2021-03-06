package es.weso.wesby

import app.models.JsonBuilder
import es.weso.wesby.sparql.ModelLoader
import org.specs2.mutable.Specification
import play.api.test.WithApplication

/**
 * Created by jorge on 08/07/14.
 */
class JsonBuilderSpec extends Specification {
  "JsonBuilder" should {
    "Generate JSON from a ResultQuery that is not empty" in new WithApplication {
      val rq = ModelLoader.loadUri("webindex/v2013/region/Africa")
      val json = JsonBuilder.toJson(rq)
      json.values must not beEmpty
    }
  }

}
