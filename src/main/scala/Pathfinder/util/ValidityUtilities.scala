package Pathfinder.util

import scalafx.scene.input.KeyCode

// Enum for movement directions
object Movement extends Enumeration {
  type Movement = Value
  val Left, Right, Up, Down = Value
}

object ValidityUtilities {
  // Check key validity
  def isValidKey(keyCode: KeyCode): Boolean = {
    keyCode match {
      // Only accept specific keys
      case KeyCode.Up | KeyCode.Down | KeyCode.Left | KeyCode.Right =>
        true
      case _ =>
        false
    }
  }

  // Overloading method for checking login input
  def isInputValid(username: String, password: String): String = {
    var errorMessage = ""
    // Logic A: Check if the input is valid with 2 parameters
    if(username.isEmpty) {
      errorMessage += "Username cannot be empty.\n"
    }
    if (password.isEmpty) {
      errorMessage += "Password cannot be empty.\n"
    }
    errorMessage
  }

  // Overloading method for checking registration input
  def isInputValid(username: String, password: String, confirmPassword: String): String = {
    var errorMessage = ""
    // Logic B: Check if the input is valid with 3 parameters
    if (username.isEmpty) {
      errorMessage += "Username cannot be empty.\n"
    }
    if (password.isEmpty) {
      errorMessage += "Password cannot be empty.\n"
    }
    if (password != confirmPassword) {
      errorMessage += "Passwords do not match.\n"
    }

    errorMessage
  }
}