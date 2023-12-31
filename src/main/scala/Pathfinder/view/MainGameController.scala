package Pathfinder.view
import Pathfinder.MainApp
import Pathfinder.model.CellType._
import Pathfinder.model.Player
import Pathfinder.util.{BaseGameController, Movement, ValidityUtilities}
import javafx.scene.image.ImageView
import javafx.scene.input.KeyEvent
import javafx.scene.layout.GridPane
import scalafx.Includes._
import scalafx.scene.input.KeyCode
import scalafxml.core.macros.sfxml

import scala.annotation.tailrec

@sfxml
class MainGameController extends BaseGameController{

  var player: Player = null
  private var isObjectMoving: Boolean = false
  private var isExit: Boolean = false

  // Method to handle key presses
  override def onKeyPressed(event: KeyEvent): Unit = {
    if (!isObjectMoving) {
      handleKeyPress(event.getCode)
    }
  }

  // Handle key press
  private def handleKeyPress(keyCode: KeyCode): Unit = {
    // Check if the pressed key is one of the valid keys
    if (ValidityUtilities.isValidKey(keyCode)) {
      // Interpret the pressed key
      val movement = interpretKey(keyCode)

      // Check if the movement is valid
      if (movement.isDefined) {
        // Move the object based on the interpreted movement
        val gameStatus = moveObject(movement.get)
        // If player reaches exit, show game over screen
        if (gameStatus) {
          // Get the amount of movements
          val movementCount = movementCounter.getOrElse(throw new RuntimeException("Movement counter is not defined")).getText.toInt
          MainApp.showGameOver(movementCount, player)
        }
      }
    }
  }

  // Interpret a single key and return the corresponding movement
  private def interpretKey(keyCode: KeyCode): Option[Movement.Movement] = {
    keyCode match {
      case KeyCode.Left => Some(Movement.Left)
      case KeyCode.Right => Some(Movement.Right)
      case KeyCode.Up => Some(Movement.Up)
      case KeyCode.Down => Some(Movement.Down)
      case _ => None
    }
  }

  // Simulate moving object based on key inputs
  private def moveObject(movement: Movement.Movement): Boolean = {
    isObjectMoving = true
    // Check if the pathfinderObject is defined
    val mainObject = pathfinderObject.getOrElse(throw new RuntimeException("Pathfinder object is not defined"))

    // Recursive function for continuous left movement
    @tailrec
    def moveLeft(mainObject: ImageView, rowIndex: Int, columnIndex: Int): Unit = {
      val nextObjectColumn = columnIndex - 1
      val nextObjectRow = rowIndex

      // Check if the new column index is valid
      val cellType = maze.getCellType(nextObjectRow, nextObjectColumn)

      cellType match {
        case Some(node) if node == Wall =>
          // Wall image found
          println(s"You hit a wall at row $nextObjectRow, column $nextObjectColumn!")
        case Some(node) if node == Exit =>
          // Exit image found
          println(s"You reached the exit at row $nextObjectRow, column $nextObjectColumn!")
          isExit = true
        case None =>
          // Handle the case where no node is found
          GridPane.setColumnIndex(mainObject, columnIndex - 1)
          // Continue moving left recursively
          moveLeft(mainObject, nextObjectRow, nextObjectColumn)
      }
    }

    @tailrec
    def moveRight(mainObject: ImageView, rowIndex: Int, columnIndex: Int): Unit = {
      val nextObjectColumn = columnIndex + 1
      val nextObjectRow = rowIndex

      // Check if the new column index is valid
      val cellType = maze.getCellType(nextObjectRow, nextObjectColumn)

      cellType match {
        case Some(node) if node == Wall =>
          // Wall image found
          println(s"You hit a wall at row $nextObjectRow, column $nextObjectColumn!")
        case Some(node) if node == Exit =>
          // Exit image found
          println(s"You reached the exit at row $nextObjectRow, column $nextObjectColumn!")
          isExit = true
        case None =>
          // Handle the case where no node is found
          GridPane.setColumnIndex(mainObject, columnIndex + 1)
          // Continue moving right recursively
          moveRight(mainObject, nextObjectRow, nextObjectColumn)
      }
    }

    @tailrec
    def moveUp(mainObject: ImageView, rowIndex: Int, columnIndex: Int): Unit = {
      val nextObjectColumn = columnIndex
      val nextObjectRow = rowIndex - 1

      // Check if the new row index is valid
      val cellType = maze.getCellType(nextObjectRow, nextObjectColumn)

      cellType match {
        case Some(node) if node == Wall =>
          // Wall image found
          println(s"You hit a wall at row $nextObjectRow, column $nextObjectColumn!")
        case Some(node) if node == Exit =>
          // Exit image found
          println(s"You reached the exit at row $nextObjectRow, column $nextObjectColumn!")
          isExit = true
        case None =>
          // Handle the case where no node is found
          GridPane.setRowIndex(mainObject, rowIndex - 1)
          // Continue moving up recursively
          moveUp(mainObject, nextObjectRow, nextObjectColumn)
      }
    }

    @tailrec
    def moveDown(mainObject: ImageView, rowIndex: Int, columnIndex: Int): Unit = {
      val nextObjectColumn = columnIndex
      val nextObjectRow = rowIndex + 1

      // Check if the new row index is valid
      val cellType = maze.getCellType(nextObjectRow, nextObjectColumn)

      cellType match {
        case Some(node) if node == Wall =>
          // Wall image found
          println(s"You hit a wall at row $nextObjectRow, column $nextObjectColumn!")
        case Some(node) if node == Exit =>
          // Exit image found
          println(s"You reached the exit at row $nextObjectRow, column $nextObjectColumn!")
          isExit = true
        case None =>
          // Handle the case where no node is found
          GridPane.setRowIndex(mainObject, rowIndex + 1)
          // Continue moving down recursively
          moveDown(mainObject, nextObjectRow, nextObjectColumn)
      }
    }

    // Process each movement in the sequence
    movement match {
      case Movement.Left => moveLeft(mainObject, GridPane.getRowIndex(mainObject), GridPane.getColumnIndex(mainObject))
      case Movement.Right => moveRight(mainObject, GridPane.getRowIndex(mainObject), GridPane.getColumnIndex(mainObject))
      case Movement.Up => moveUp(mainObject, GridPane.getRowIndex(mainObject), GridPane.getColumnIndex(mainObject))
      case Movement.Down => moveDown(mainObject, GridPane.getRowIndex(mainObject), GridPane.getColumnIndex(mainObject))
    }

    // Increase the movement counter by 1 and update the label
    val currentMovementCount = movementCounter.get.getText.toInt
    movementCounter.get.setText((currentMovementCount + 1).toString)


    // Set flag to indicate object has stopped moving
    isObjectMoving = false
    return isExit
  }
  // Method to reset the game
  def resetGame(): Unit = {
    resetGame(movementCounter.getOrElse(throw new RuntimeException("Movement counter is not defined")),
      pathfinderObject, initialRowIndex, initialColumnIndex)

    // Reset the exit flag
    isExit = false
  }
}