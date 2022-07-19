package ch.makery.todo.util
import scalikejdbc._
import ch.makery.todo.model.Task
//Reference : OOP Tutorial Address App

trait Database {
  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"

  val dbURL = "jdbc:derby:todoDB;create=true;";

  Class.forName(derbyDriverClassname) 

  ConnectionPool.singleton(dbURL, "chi", "xwei") 
    
  implicit val session = AutoSession

}

object Database extends Database{
  def setupDB() = {  
  	if (!hasDBInitialize)
  		Task.initializeTable()
  }
  def hasDBInitialize : Boolean = {

    DB getTable "Task" match {
      case Some(x) => true
      case None => false
    }
    
  }
}
