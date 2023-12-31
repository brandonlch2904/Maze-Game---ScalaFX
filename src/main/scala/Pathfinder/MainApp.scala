package Pathfinder
import Pathfinder.model.Player
import Pathfinder.util.Database
import Pathfinder.view.{GameOverController, LeaderboardController, MainGameController, MainMenuController}
import scalafx.scene.image.Image
import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import javafx.scene.control.Label
import scalafx.stage.{Modality, Stage}
import scalafxml.core.{FXMLLoader, NoDependencyResolver}

object MainApp extends JFXApp {
  // Initialize database
  Database.setupDB()
  // Get all necessary layouts
  val rootResource = getClass.getResource("view/RootLayout.fxml")
  val mainGameResource = getClass.getResource("view/MainGame.fxml")
  // Get the loader for the resources
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  val mainGameLoader = new FXMLLoader(mainGameResource, NoDependencyResolver)
  // Preload the resources
  loader.load()
  mainGameLoader.load()
  // Retrieve the root component BorderPane from the FXML
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  // Initialize stage
  stage = new PrimaryStage {
    title = "Pathfinder"
    icons += new Image(getClass.getResourceAsStream("images/pathfindericon.jpg"))
    scene = new Scene {
      stylesheets += getClass.getResource("view/style.css").toString
      root = roots
    }
  }

  def showLogin(): Unit = {
    val resource = getClass.getResource("view/Login.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val loginRoot = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(loginRoot)
  }

  def showRegister(): Unit = {
    val resource = getClass.getResource("view/Register.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val registerRoot = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(registerRoot)
  }

  // actions for display person overview window
  def showMainMenu(player: Player): Unit = {
    val resource = getClass.getResource("view/MainMenu.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val mainMenuRoot = loader.getRoot[jfxs.layout.BorderPane]
    this.roots.setCenter(mainMenuRoot)

    val controller = loader.getController[MainMenuController#Controller]
    controller.player = player
  }

  def startGame(player: Player): Unit = {
    // Load MainGame.fxml
    val mainGameRoot = mainGameLoader.getRoot[jfxs.layout.BorderPane]
    // Set Game Layout
    this.roots.setCenter(mainGameRoot)
    // Get the controllers
    val mainGameController = mainGameLoader.getController[MainGameController#Controller]
    // Main Game Controller
    mainGameController.mainGameRoot = Option(mainGameRoot)
    mainGameController.initialize()
    mainGameController.player = player
  }

  def showGameOver(movementCount: Int, player: Player): Unit = {
    println("Game Over")
    val resource = getClass.getResource("view/GameOver.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.Parent]
    val controller = loader.getController[GameOverController#Controller]

    val dialog = new Stage() {
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      title = "Game Over"
      icons += new Image(getClass.getResourceAsStream("images/pathfindericon.jpg"))
      scene = new Scene {
        stylesheets += getClass.getResource("view/style.css").toString
        root = roots
      }
    }
    controller.gameOverDialog = Option(dialog)
    controller.movementCount = movementCount
    controller.player = player
    controller.currentScoreLabel = Option(roots.lookup("#currentScore").asInstanceOf[Label])
    controller.highestScoreLabel = Option(roots.lookup("#highestScore").asInstanceOf[Label])
    controller.initialize(mainGameLoader.getController[MainGameController#Controller])

    dialog.showAndWait()
  }

  def showLeaderboard(player: Player): Unit = {
    val resource = getClass.getResource("view/Leaderboard.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.Parent]
    this.roots.setCenter(roots)
    val controller = loader.getController[LeaderboardController#Controller]
    controller.initialize()
    controller.player = player
  }

  def quitGame(): Unit = {
    stage.close()
  }
  // call to display Login Page when app start
  showLogin()
}
