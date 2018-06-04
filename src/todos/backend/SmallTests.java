package todos.backend;

import java.time.LocalDate;

import inout.Out;

public class SmallTests {
	
	static int y = 2017;
	static int m = 3;
	static int d = 1;
	static String desc = "Task for ";
	
	static int count = 4;
	
	private static String getDesc() {
		return desc + y + "-" + m + "-" + d;
	}
	
	private static void printTodoList(Todo[] TodoList, String comment) {
		Out.println(comment);
		for (Todo currTodo : TodoList) {
			Out.println("    " + currTodo);
		}
	}
	
	private static void showAll() {
		printTodoList(TodoManager.getAll(),"Showing the whole list:");
	}

	public static void main(String[] args) {
		
		for (int i = 0; i < count; i++ ) {
			LocalDate dueDate = LocalDate.of(y, m, d);
			TodoManager.newTodo(getDesc(), dueDate);
			d += 1;
		}
		
//		showAll();
		
//		TodoManager.delete(1);
		TodoManager.markDone(2);
		TodoManager.markDone(1);
//		TodoManager.markDone(3);
		showAll();
		
		TodoManager.deleteDoneUnto(LocalDate.of(y, m, d));
		
		showAll();
		
//		printTodoList(TodoManager.getAllOpen(),"Showing all open ones:");

	}

}
