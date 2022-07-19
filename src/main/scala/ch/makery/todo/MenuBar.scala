package ch.makery.todo
import scalafxml.core.macros.sfxml
import scala.util.Try
//Reference : OOP Tutorial Address App

@sfxml
class MenuBar() {
    def closeWindow() {
        System.exit(0)
    }

    def deleteTask() {

        Try(Main.loader.getController[ToDoTask#Controller].completeTask())
    }
}