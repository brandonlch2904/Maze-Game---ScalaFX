package Pathfinder.util

import Pathfinder.model.Maze
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.input.KeyEvent
import javafx.scene.layout.{BorderPane, GridPane}

abstract class BaseGameController extends Resettable{

  var mainGameRoot: Option[BorderPane] = None
  var pathfinderObject: Option[ImageView] = None
  var initialRowIndex: Int = 0
  var initialColumnIndex: Int = 0
  var movementCounter: Option[Label] = None
  var maze: Maze = _

  // Initialize method
  def initialize(): Unit = {

    // Set up the key event handling
    mainGameRoot.getOrElse(throw new RuntimeException("Main game root is not defined"))
      .setOnKeyPressed((event: KeyEvent) => onKeyPressed(event))

    // Ensure the root is focusable
    mainGameRoot.getOrElse(throw new RuntimeException("Main game root is not defined"))
      .requestFocus()

    // Find the pathfinderObject in the maze
    pathfinderObject = Option(mainGameRoot.getOrElse(throw new RuntimeException("Main game root is not defined"))
      .lookup("#pathfinderObject").asInstanceOf[ImageView])

    // Get the initial row and column index of the pathfinderObject
    initialRowIndex = GridPane.getRowIndex(pathfinderObject.get)
    initialColumnIndex = GridPane.getColumnIndex(pathfinderObject.get)

    // Initialize the maze
    maze = new Maze(mainGameRoot.getOrElse(throw new RuntimeException("Main game root is not defined"))
      .lookup("#maze").asInstanceOf[GridPane])

    // Get the movement counter
    movementCounter = Option(mainGameRoot.getOrElse(throw new RuntimeException("Main game root is not defined"))
      .lookup("#movementCounter").asInstanceOf[Label])
  }

  // Abstract method to handle key presses
  def onKeyPressed(event: KeyEvent): Unit
}
