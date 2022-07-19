package ch.makery.todo
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader}
import javafx.{scene => jfxs}
import scalafx.stage.{Stage, Modality}
import scalafx.scene.image.Image
import ch.makery.todo.model.Task
//Reference : OOP Tutorial Address App

object Main extends JFXApp {

    val rootResource = getClass.getResource("MenuBar.fxml")
    var loader = new FXMLLoader(rootResource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.BorderPane]

    stage = new PrimaryStage {
    title = "To-Do List"
    icons += new Image(getClass.getResourceAsStream("image/icon.png")) 
    scene = new Scene {
      root = roots
      stylesheets = List(getClass.getResource("Theme.css").toString) 
    }
  }

    def displayWindow(getWindow: String): Unit = {
      val resource = getClass.getResource(s"${getWindow}")
      loader = new FXMLLoader(resource, NoDependencyResolver)
      loader.load();
      val roots = loader.getRoot[jfxs.layout.AnchorPane]
      this.roots.setCenter(roots)

  } 

  displayWindow("Welcome.fxml")

  def taskDialog(task: Task): Boolean = {
    val resource = getClass.getResource("AddNewTask.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[TaskDialog#Controller]

    val dialog = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots2
        stylesheets = List(getClass.getResource("Theme.css").toString) 
      }
    }
    control.dialogStage = dialog
    control.task = task
    dialog.showAndWait()
    control.okClicked
  } 

}

