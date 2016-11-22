package dao

import scala.concurrent.Future

import javax.inject.Inject
import models.Users
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

class UserDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val User = TableQuery[UsersTable]

  def all(): Future[Seq[Users]] = db.run(User.result)

  def insert(user: Users): Future[Unit] = db.run(User += user).map { _ => () }

  private class UsersTable(tag: Tag) extends Table[Users](tag, "USERS") {

  	def id = column[Long]("ID")
    def username = column[String]("USERNAME")
    def password = column[String]("PASSWORD")

    def * = (id, username, password) <> (Users.tupled, Users.unapply _)
  }
}