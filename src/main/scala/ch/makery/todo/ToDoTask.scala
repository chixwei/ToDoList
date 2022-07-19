package ch.makery.todo
import ch.makery.todo.model.Task
import scalafx.scene.control.{TableView, TableColumn, Label}
import scalafxml.core.macros.sfxml
import scalafx.beans.property.{StringProperty}
import scalafx.Includes._
import ch.makery.todo.util.DateUtil._
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
//Reference : OOP Tutorial Address App


@sfxml
class ToDoTask(
  
    private val taskTable     : TableView[Task],
   
    private val taskList      : TableColumn[Task, String],

    private val priorityList  : TableColumn[Task, String],

    private val taskLabel     : Label,
    
    private val priorityLabel :  Label,

    private val dateLabel     : Label,

    private val locationLabel : Label,
  
    private val detailLabel   : Label
    
    ) {

  taskTable.items = Task.taskData

  taskList.cellValueFactory = {_.value.tasks}
  priorityList.cellValueFactory = {_.value.priority}

    
  private def showTaskDetail (task : Option[Task]) = {
        task match {

            case Some(task) =>

                taskLabel.text      <== task.tasks
                priorityLabel.text  <== task.priority;
                dateLabel.text = task.date.value.asString
                locationLabel.text  <== task.location
                detailLabel.text    <== task.detail

            
            case None =>

                taskLabel.text.unbind()
                priorityLabel.text.unbind()
                locationLabel.text.unbind()
                detailLabel.text.unbind()
                taskLabel.text      = ""
                priorityLabel.text  = ""
                dateLabel.text      = ""
                locationLabel.text  = ""
                detailLabel.text    = ""

       }    

    }

    showTaskDetail(None)

    taskTable.selectionModel().selectedItem.onChange(
        (_, _, newValue) => showTaskDetail(Option(newValue))
    )

    def completeTask() = {
        val selectedIndex = taskTable.selectionModel().selectedIndex.value
            if (selectedIndex >= 0) {
                taskTable.items().remove(selectedIndex).deleteTask(); 

            } 
            else {
            
                val alert = new Alert(AlertType.Error){
                initOwner(Main.stage)
                title       = "No Selection"
                headerText  = "No Task Selected"
                contentText = "Please select a task in the table."
                }.showAndWait()
            }

    } 


    def newTask(action : ActionEvent) = {
        val task = new Task("", "")
        val okClicked = Main.taskDialog(task);
            if (okClicked) {
                Task.taskData += task
                showTaskDetail(Some(task)) 
                task.save() 
            }
    }


    def editTask(action : ActionEvent) = {
    val selectedTask: Task = taskTable.selectionModel().selectedItem.value
    if (selectedTask != null) {
        val okClicked = Main.taskDialog(selectedTask)

        if (okClicked) {
            showTaskDetail(Some(selectedTask))
            selectedTask.save()
        }

    } else {

        val alert = new Alert(Alert.AlertType.Warning){
        initOwner(Main.stage)
        title       = "No Selection"
        headerText  = "No Task Selected"
        contentText = "Please select a task in the table."
        }.showAndWait()
    }
} 

}