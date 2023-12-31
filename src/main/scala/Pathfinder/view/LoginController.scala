package Pathfinder.view

import Pathfinder.MainApp
import Pathfinder.model.Player
import Pathfinder.util.ValidityUtilities
import javafx.scene.control.{PasswordField, TextField}
import scalafx.scene.control.Alert
import scalafxml.core.macros.sfxml

@sfxml
class LoginController(private val loginUsername: TextField,
                      private val loginPassword: PasswordField) {


  def loginUser = {
    val username: String = loginUsername.getText
    val password: String = loginPassword.getText
    val errors = ValidityUtilities.isInputValid(username, password)

    if(errors.isEmpty) {
      // Create a Player instance with the entered credentials
      val playerToLogin = new Player(username, password)

      // Check if the login is successful
      if (playerToLogin.login()) {
        // Handle successful login
        println("Login successful!")
        // Navigate to the main menu screen
        MainApp.showMainMenu(playerToLogin)
      } else {
        // Handle unsuccessful login
        println("Invalid username or password.")
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

  // Show the register screen
  def showRegister(): Unit = {
     MainApp.showRegister()
  }

}
