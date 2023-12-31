package Pathfinder.view
import Pathfinder.MainApp
import Pathfinder.model.Player
import javafx.event.ActionEvent
import scalafxml.core.macros.sfxml

@sfxml
class MainMenuController {
  var player: Player = null

  def handleStartButton(): Unit = {
    MainApp.startGame(player)
  }

  def handleLeaderboardButton(event: ActionEvent): Unit = {
    MainApp.showLeaderboard(player)
  }

  def handleQuit = {
    MainApp.quitGame()
  }
}
