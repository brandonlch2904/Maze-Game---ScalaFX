package Pathfinder.model

import scalafx.beans.property.{ObjectProperty, StringProperty}

case class Leaderboard(playerName: StringProperty, playerScore: ObjectProperty[Int])
