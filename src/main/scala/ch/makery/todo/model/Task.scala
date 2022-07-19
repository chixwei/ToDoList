package ch.makery.todo.model
import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty}
import java.time.LocalDate
import ch.makery.todo.util.Database
import ch.makery.todo.util.DateUtil._
import scalikejdbc._
import scala.util.{ Try, Success, Failure }
import scalafx.collections.ObservableBuffer
//Reference : OOP Tutorial Address App

class Task (val taskName : String, val priorityType : String) extends Database {
	def this()     	= this(null, null)
	var tasks  		= new StringProperty(taskName)
	var priority  	= new StringProperty("Normal")
	var date       	= ObjectProperty[LocalDate](LocalDate.now)
	var location    = new StringProperty()
	var detail    	= new StringProperty()

	def save() : Try[Int] = {
		if (!(isExist)) { 

			Try(DB autoCommit { implicit session => 
				sql"""
					insert into task (tasks, priority,
						date, location, detail) values 
						(${tasks.value}, ${priority.value}, ${date.value.asString}, 
							${location.value}, ${detail.value})		
				""".update.apply()
			})
		} else {
            
			Try(DB autoCommit { implicit session => 
				sql"""
				update task 
				set 
				tasks  		= ${tasks.value} ,
				priority  	= ${priority.value},
				date        = ${date.value.asString},
				location    = ${location.value},
				detail    	= ${detail.value}
				 where tasks = ${tasks.value}
				""".update.apply()
			})
		}
			
	}
	def deleteTask() : Try[Int] = {
		if (isExist) {
			Try(DB autoCommit { implicit session => 
			sql"""
				delete from task where  
					tasks = ${tasks.value} and priority = ${priority.value}
				""".update.apply()
			})
		} else 
			throw new Exception("Task not Exists in Database")		
	}
	def isExist : Boolean =  {
		DB readOnly { implicit session =>
			sql"""
				select * from task where 
				tasks = ${tasks.value} 
			""".map(rs => rs.string("tasks")).single.apply()
		} match {
			case Some(x) => true 
			case None => false
		}

	}

}
object Task extends Database{  
    
    //describe all the instances of task

    val taskData = new ObservableBuffer[Task]()
    Database.setupDB() 
    taskData ++=Task.getAllTask

	def apply (
		taskName 	 : String, 
		priorityType : String,
		dates 		 : String,
		locations 	 : String,
		details 	 : String
		) : Task = {

		new Task(taskName, priorityType) {
			priority.value 	 = priorityType
			date.value       = dates.parseLocalDate
			location.value   = locations
			detail.value     = details

		}
		
	}

	def initializeTable() = {

		DB autoCommit { implicit session =>  
			sql"""
			create table task (
			  id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
			  tasks varchar(100), 
			  priority varchar(64),
			  date varchar(64), 
			  location varchar(100),
			  detail varchar(300)
			)
			""".execute.apply()
		}
	}
	
	def getAllTask : List[Task] = {
		DB readOnly { implicit session =>
			sql"select * from task".map(rs => Task(rs.string("tasks"), 
				rs.string("priority"),rs.string("date"), 
				rs.string("location"), rs.string("detail") )).list.apply()
		}
	}
}
