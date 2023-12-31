package Pathfinder.util

import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane

trait Resettable {
  def resetGame(movementCounter: Label, pathfinderObject: Option[ImageView], initialRowIndex: Int, initialColumnIndex: Int): Unit = {
    // Reset the movement counter
    movementCounter.setText("0")

    // Reset the object's position to the initial position
    pathfinderObject.foreach { obj =>
      GridPane.setRowIndex(obj, initialRowIndex)
      GridPane.setColumnIndex(obj, initialColumnIndex)
    }
  }
}
