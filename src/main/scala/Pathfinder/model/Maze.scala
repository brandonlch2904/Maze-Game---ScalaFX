package Pathfinder.model

import javafx.scene.layout.GridPane
import scalafx.Includes._

object CellType extends Enumeration {
  type CellType = Value
  val Wall, Exit = Value
}

class Maze(mazeGrid: GridPane) {
  import CellType._
  // Function to get the content of a specific cell in the GridPane
  def getCellType(row: Int, column: Int): Option[CellType] = {
    val children = mazeGrid.getChildren

    val nodeOption = children.find { node =>
      val nodeRow = Option(GridPane.getRowIndex(node)).getOrElse(0)
      val nodeColumn = Option(GridPane.getColumnIndex(node)).getOrElse(0)

      (nodeRow, nodeColumn) match {
        case (r, c) if r == row && c == column =>
          // If the node is at the specified row and column, return it
          true
        case _ =>
          false
      }
    }

    nodeOption.map { node =>
      if (node.getId == "wallImage") Wall
      else if (node.getId == "exitImage") Exit
      else throw new RuntimeException("Unknown cell type")
    }
  }
}
