package Pathfinder.view

import Pathfinder.MainApp
import Pathfinder.model.{Leaderboard, Player}
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{TableColumn, TableView}
import scalafxml.core.macros.sfxml

@sfxml
class LeaderboardController(
                             private val playerName: TableColumn[Leaderboard, String],
                             private val playerScore: TableColumn[Leaderboard, Int],
                             private val leaderboardTable: TableView[Leaderboard]
                           ) {

  var player: Player = null

  def initialize(): Unit = {
    // Create an ObservableBuffer to hold the data
    val leaderboardData = ObservableBuffer[Leaderboard]()
    leaderboardData ++= Player.getAllLeaderboardEntries()
    // Set the data in the TableView
    leaderboardTable.items = leaderboardData
    // Initialize the columns
    playerName.cellValueFactory = { _.value.playerName }
    playerScore.cellValueFactory = { _.value.playerScore }

  }

  def backToMainMenu(): Unit = {
    MainApp.showMainMenu(player)
  }
}
