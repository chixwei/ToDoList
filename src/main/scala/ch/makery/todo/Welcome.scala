package ch.makery.todo
import scalafxml.core.macros.sfxml
//Reference : OOP Tutorial Address App

@sfxml
class Welcome {
    def onClick() {
        Main.displayWindow("Main.fxml")
    }
}