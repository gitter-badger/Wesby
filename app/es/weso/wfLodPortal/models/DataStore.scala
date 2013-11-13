package es.weso.wfLodPortal.models

import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer

import com.hp.hpl.jena.rdf.model.{ Model => JenaModel }

trait DataStore {

  protected val map: HashMap[String, Property] = HashMap.empty

  protected def addToDataStore(p: RdfProperty, n: RdfNode) {
    val m = map.getOrElse(p.uri.relative, Property(p))
    m.nodes += n
    map += p.uri.relative -> m
  }

  def get(base: String, suffix: String): Option[Property] = {
    get(base + suffix)
  }

  def get(uri: String): Option[Property] = {
    map.get(uri)
  }

  def list = map.valuesIterator.toList

}

case class Model(
  val jenaModel: JenaModel) extends DataStore {

  def add(p: RdfProperty, r: RdfNode) { addToDataStore(p, r) }

  override def toString(): String = {
    new StringBuilder("Model[nodes:{").append(map.mkString(", "))
      .append("}]").toString
  }

}

case class InverseModel(
  val jenaModel: JenaModel) extends DataStore {

  def add(r: RdfNode, p: RdfProperty) { addToDataStore(p, r) }

  override def toString(): String = {
    new StringBuilder("InverseModel[nodes:{").append(map.mkString(", "))
      .append("}]").toString
  }
}

case class Property(val property: RdfProperty) {
  val nodes: ListBuffer[RdfNode] = ListBuffer.empty[RdfNode]
  val p = property
  val ns = nodes
}

case class LazyDataStore[T](val uri: Uri, val method: (String) => T) {
  private lazy val dataStore = method(uri.absolute)
  def data = dataStore
  def d = dataStore
  val u = uri
  val m = method
}