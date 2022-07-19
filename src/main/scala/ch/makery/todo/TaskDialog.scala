package ch.makery.todo
import ch.makery.todo.model.Task
import scalafx.scene.control.{TextField, TableColumn, Label, Alert, DatePicker, ChoiceBox, TextArea}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.Includes._
import ch.makery.todo.util.DateUtil._
import scalafx.event.ActionEvent
import scalafx.collections.ObservableBuffer
import scalafx.beans.property.ObjectProperty
//Reference : OOP Tutorial Address App

@sfxml
class TaskDialog (

    private val    taskField     : TextField,
    private val    priorityField : ChoiceBox[String],
    private val     dateField    : DatePicker,
    private val    locationField : TextField,
    private val  detailField     : TextArea

){
  var         dialogStage : Stage  = null
  private var _task       : Task   = null
  var         okClicked            = false

//Reference : https://stackoverflow.com/questions/61520879/how-to-set-the-items-in-a-choicebox
  var priorityList = ObservableBuffer("Lowest", "Low", "Normal", "High", "Highest")
  priorityField.items = priorityList

  def task = _task
  def task_=(x : Task) {
        _task = x

        taskField.text      = _task.tasks.value
        priorityField.value = _task.priority.value
        dateField.value     = _task.date.value
        locationField.text  = _task.location.value
        detailField.text    = _task.detail.value
  }

  def saveTask(action :ActionEvent){

     if (isInputValid()) {
        _task.tasks     <== taskField.text
        _task.priority.value = priorityField.value.apply;
        _task.date.value     = dateField.value.apply;
        _task.location  <== locationField.text
        _task.detail    <== detailField.text

        okClicked = true;
        dialogStage.close()
    }
  }

  def nullChecking (x : String) = x == null || x.length == 0

  def priorityNullCheck (z : ObjectProperty[String]) = z == ""

  def isInputValid() : Boolean = {
    var errorMessage = ""

    if (nullChecking(taskField.text.value))
      errorMessage += "No valid task title!\n"
    if (errorMessage.length() == 0) {
        return true;
    } else {

        val alert = new Alert(Alert.AlertType.Error){
          initOwner(dialogStage)
          title = "Invalid Fields"
          headerText = "Please correct invalid fields"
          contentText = errorMessage
        }.showAndWait()

        return false;
    }
   }
} 
