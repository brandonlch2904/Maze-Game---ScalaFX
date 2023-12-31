package Pathfinder.util

import Pathfinder.model.Player
import scalikejdbc._

trait Database {
  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"

  val dbURL = "jdbc:derby:myDB;create=true;";
  // initialize JDBC driver & connection pool
  Class.forName(derbyDriverClassname)

  ConnectionPool.singleton(dbURL, "me", "mine")

  // ad-hoc session provider on the REPL
  implicit val session = AutoSession

}

object Database extends Database {
  def setupDB(): Unit = {
    if (!hasDBInitialize()) {
      Player.initializeTable()
    }
  }

  def hasDBInitialize(): Boolean = {
    DB getTable "Player" match {
      case Some(_) => true
      case None => false
    }
  }
}

