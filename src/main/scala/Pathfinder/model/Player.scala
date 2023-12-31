package Pathfinder.model

import Pathfinder.util.Database
import scalafx.beans.property.{IntegerProperty, ObjectProperty, StringProperty}
import scalikejdbc._

class Player (val usernameS: String, val passwordS: String) extends Database{
  def this() = this(null, null)

  var username = new StringProperty(usernameS)
  var score = ObjectProperty[Int](0)

  def login(): Boolean = {
    val query = sql"""
      select * from Player where username = ${usernameS}
    """
    println("Username: " + usernameS)

    DB readOnly { implicit session =>
      query.map(rs => Player(rs.string("username"), rs.string("password"), rs.int("score"))).single.apply()
    } match {
      case Some(existingPlayer) =>
        // Check if the password matches
        if (existingPlayer.passwordS == passwordS) {
          true
        } else {
          println("Password does not match.")
          false
        }
      case None =>
        // No player with the given username found
        println("No player with the given username found.")
        false
    }
  }

  def save(): Boolean = {
    if(!isExist()){
      // Save the player to the database
      DB autoCommit { implicit session =>
        sql"""
        insert into Player (username, password, score) values (${usernameS}, ${passwordS}, ${score.value})
        """.update.apply()
      }
      println("Player saved to database.")
      true
    } else {
      false
    }
  }

  def updateScore(): Boolean = {
    if(isExist()){
      // Save the player to the database
      DB autoCommit { implicit session =>
        sql"""
        update Player set score = ${score.value} where username = ${usernameS}
        """.update.apply()
      }
      println("Score updated to database.")
      true
    } else {
      false
    }
  }

  def isExist(): Boolean = {
    DB readOnly { implicit session =>
      sql"""
      select * from Player where username = ${usernameS}
      """.map(rs => Player(rs.string("username"), rs.string("password"), rs.int("score"))).single.apply()
    } match {
      case Some(existingPlayer) => true
      case None => false
    }
  }

  def getScore(): Option[Int] = {
    DB readOnly { implicit session =>
      sql"""
      select score from Player where username = ${usernameS}
      """.map(rs => rs.int("score")).single.apply()
    }
  }
}

object Player extends Database{
  def apply (usernameS: String, passwordS: String, scoreS: Int): Player = {
    new Player(usernameS, passwordS){
      score.value = scoreS
    }
  }

  def initializeTable(): Unit = {
    DB autoCommit { implicit session =>
      sql"""
      create table Player (
        username varchar(64) not null primary key,
        password varchar(64) not null,
        score int not null
      )
      """.execute.apply()
    }
  }

  def getAllLeaderboardEntries(): List[Leaderboard] = {
    DB readOnly { implicit session =>
      sql"""
    SELECT * FROM Player WHERE score > 0 ORDER BY score ASC
    """.map(rs => Leaderboard(StringProperty(rs.string("username")), ObjectProperty(rs.int("score")))).list.apply()
    }
  }

}
