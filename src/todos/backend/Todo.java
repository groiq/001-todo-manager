package todos.backend;

import java.time.LocalDate;
//import static todos.app.Todo.Status.*;
enum Status { Open, Done }

public class Todo {
	
//	enum Status { Open, Done }

	final int id;
	final String description;
	LocalDate dueDate; // one could change due date later
	Status status;
	Todo nextTodo;
	
	Todo(int id, String description, LocalDate dueDate) {
		this.id = id;
		this.description = description;
		this.dueDate = dueDate;
		this.status = Status.Open;
		this.nextTodo = null;
		
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Todo getNextTodo() {
		return nextTodo;
	}

	void setNextTodo(Todo nextTodo) {
		this.nextTodo = nextTodo;
	}

	@Override
	public String toString() {
		int tabNum = 30 - description.length();
		String tabulator = "";
		for (int i=1; i < tabNum; i++) { tabulator = tabulator + " "; }
		return id + ": " + dueDate + " - " + description + tabulator + ": " + status;
//		return "Todo [id=" + id + ", description=" + description + ", dueDate=" + dueDate + ", status=" + status
//				+  "]";
	}

	
	
	

}
