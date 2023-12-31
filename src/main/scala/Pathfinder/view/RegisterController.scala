package Pathfinder.view

import Pathfinder.MainApp
import Pathfinder.model.Player
import Pathfinder.util.ValidityUtilities.isInputValid
import javafx.scene.control.{PasswordField, TextField}
import scalafx.scene.control.Alert
import scalafxml.core.macros.sfxml

@sfxml
class RegisterController(
                        private val registerUsername: TextField,
                        private val registerPassword: PasswordField,
                        private val registerConfirmPassword: PasswordField,
                        ) {


  def registerUser(): Unit = {
    val username: String = registerUsername.getText
    val password: String = registerPassword.getText
    val confirmPassword: String = registerConfirmPassword.getText
    val errors = isInputValid(username, password, confirmPassword)

    if(errors.isEmpty) {
      val username = registerUsername.getText
      val password = registerPassword.getText

      val player = new Player(username, password)

      if (!player.isExist()) {
        if (player.save()) {
          println("Registration successful!")
          showLogin()
        } else {
          println("Registration failed. Unable to save to the database.")
        }
      } else {
        println("Username already exists. Choose a different username.")
      }
    }else{
      // Show error message
      val alert = new Alert(Alert.AlertType.Error) {
        initOwner(MainApp.stage)
        title = "Invalid Input"
        headerText = "Please correct invalid fields"
        contentText = errors
      }.showAndWait()
    }
  }

  def showLogin(): Unit = {
    // Clear the text fields
    registerUsername.setText("")
    registerPassword.setText("")
    registerConfirmPassword.setText("")
    // Navigate to the login screen
    MainApp.showLogin()
  }

}
