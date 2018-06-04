package todos.app;

import java.time.LocalDate;

import inout.In;
import inout.Out;
import todos.backend.Todo;
import todos.backend.TodoManager;

public class TodosMain {
	
	private static LocalDate march9 = LocalDate.of(2017, 3, 9);

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
		
		In.open("todos.txt");
		if (!In.done()) {
			Out.println("Cannot open file todos.txt"); 
			return; 
		}
		
////		int year = In.readInt(); 
//		int year;
//		int month;
//		int day;
//		String descr;
//		while (In.done()) {
//			year = In.readInt();
//			month = In.readInt();
//			day = In.readInt();
//			descr = In.readString();
////			int month = In.readInt(); 
////			int day = In.readInt(); 
////			String descr = In.readString(); 
////
////			int year = In.readInt();
		
		int year = In.readInt(); 
		while (In.done()) {
			int month = In.readInt(); 
			int day = In.readInt(); 
			String descr = In.readString(); 

			LocalDate dueDate = LocalDate.of(year, month, day);
//			Out.println(dueDate + descr);
			
			TodoManager.newTodo(descr,dueDate); // comment this out to test for empty list
			
			year = In.readInt();

		}
		
		In.close(); 
//		TodoManager.printAll(); // debug only!
		
		showAll();
		
		Out.println("Get Todos by id:");
		for (int i = 0; i < 11; i++) {
			Out.print(i + ": ");
			Todo foundTodo = TodoManager.getTodo(i);
			if (foundTodo == null
					) {
				Out.print("Not found");
			} else {
				Out.print(foundTodo);
			}
			Out.println();
		}
		
//		Out.println("comparing getAll() with getting all through an empty filter:");
//		Todo[] unfilteredList = TodoManager.getAll();
//		Todo[] filteredList = TodoManager.getAllButFilteredButActuallyNot();
//		if ( filteredList.length != unfilteredList.length) {
//			Out.print("Error: Different List sizes!");
//		}
//		int length = ( (filteredList.length < unfilteredList.length) ? filteredList.length : unfilteredList.length );
//		for (int i = 0; i < length; i++) {
//			Todo filtered = filteredList[i];
//			Todo unfiltered = unfilteredList[i];
//			Out.println(filtered);
//			Out.println(unfiltered);
//			if (filtered != unfiltered) { Out.println("Error: different Todo entries!"); }
//			else { Out.println("Match"); }
//		}
		
		
		
		Out.println("Marking a few as done...");
		TodoManager.markDone(1);
		TodoManager.markDone(2);
		TodoManager.markDone(4);
		TodoManager.markDone(5);
		TodoManager.markDone(6);
		// TODO mark a few as done
		
		showAll();
		printTodoList(TodoManager.getAllDone(),"Done:");
		printTodoList(TodoManager.getAllOpen(),"still open:");
		printTodoList(TodoManager.getOpenUntil(march9), "still open until March 9:");
		printTodoList(TodoManager.getDoneUntil(march9), "Done until March 9:");
		printTodoList(TodoManager.getAllUntil(march9), "All until March 9:");
		
		showAll();
//		Out.println("Deleting one...");
//		Delete from empty list
//		Delete first
		
//		delete middle
//		delete last
//		delete one that isn't there
//		TodoManager.delete(6);
				
		
		
//		Out.println("Showing all again:");
//		showAll();
		
		Out.println("Deleting all done until March 9...");
		TodoManager.deleteDoneUnto(march9);
		
		showAll();
		
		
		
		
		
	}



}
