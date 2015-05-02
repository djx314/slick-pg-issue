package org.xarcher.pgtest

import com.github.tminglei.slickpg._
import play.api.libs.json._

import slick.collection.heterogeneous._

object MyPostgresDriver extends slick.driver.PostgresDriver
with PgArraySupport
with PgDateSupport
with PgRangeSupport
with PgHStoreSupport
with PgPlayJsonSupport
with PgSearchSupport
with PgPostGISSupport
with array.PgArrayJdbcTypes {

  override val pgjson = "jsonb"

  val api = new API with JsonImplicits {
    implicit val strListTypeMapper: DriverJdbcType[List[String]] = new SimpleArrayJdbcType[String]("text").to(_.toList)
    implicit val json4sJsonArrayTypeMapper: DriverJdbcType[List[JsValue]] =
      new AdvancedArrayJdbcType[JsValue](pgjson,
        (s) => utils.SimpleArrayUtils.fromString[JsValue](Json.parse(_))(s).orNull,
        (v) => utils.SimpleArrayUtils.mkString[JsValue](_.toString())(v)
      ).to(_.toList)
  }

  val plainAPI = new API with PlayJsonPlainImplicits

}

case class User(id: Option[Long], info: Option[JsValue])

import MyPostgresDriver.api._

class UserTable(tag: Tag) extends Table[User](tag, "p_user") {

  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def info = column[Option[JsValue]]("u_info")
  
  def * =
    (id ::
      info ::
      HNil
      ).shaped <> (
      { case x => User(
        x(0),
        x(1)
      )}, ({ x: User =>
      Option((
        x.id ::
        x.info ::
        HNil
      ))
    }))
	
}

object Test extends App {
  val userTable = TableQuery[UserTable]
}
