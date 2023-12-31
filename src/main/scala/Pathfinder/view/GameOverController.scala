package Pathfinder.view

import Pathfinder.MainApp
import Pathfinder.model.Player
import scalafx.scene.control.Label
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

@sfxml
class GameOverController{

  var player: Player = null
  var gameOverDialog: Option[Stage] = None
  var mainGameController: Option[MainGameController#Controller] = None
  var movementCount: Int = 0
  var currentScoreLabel: Option[Label] = None
  var highestScoreLabel: Option[Label] = None

  def initialize(controller: MainGameController#Controller): Unit = {
    mainGameController = Option(controller)

    // Get player's highest score
    val score = player.getScore().getOrElse(throw new RuntimeException("Player score is not found"))

    // Set the score label
    currentScoreLabel.get.setText(movementCount.toString)
    highestScoreLabel.get.setText(score.toString)
  }

  def resetGame(): Unit = {
    // Close the game over dialog
    gameOverDialog.getOrElse(throw new RuntimeException("Game over dialog is not defined")).close()
    // Update score
    savePlayerScore()

    // Reset the game through the MainGameController
    mainGameController.foreach(_.resetGame())
  }

  def returnToMainMenu(): Unit = {
    // Close the game over dialog
    gameOverDialog.getOrElse(throw new RuntimeException("Game over dialog is not defined")).close()
    // Update score
    savePlayerScore()

    // Reset the game through the MainGameController
    mainGameController.foreach(_.resetGame())

    // Show the main menu
    MainApp.showMainMenu(player)

  }

  def savePlayerScore(): Unit = {
    // Get the player current score
    val score = player.getScore().getOrElse(throw new RuntimeException("Player score is not found"))
    println("Previous: " + score)
    println("Current: " + movementCount)
    // Compare the score with the current score
    if (score == 0 || score > movementCount) {
      // Update the player score
      player.score.value = movementCount
      player.updateScore()
    }
  }

}
