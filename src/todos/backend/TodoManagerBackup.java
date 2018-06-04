package todos.backend;

import java.time.LocalDate;
import inout.Out;

//public class Test {}

public class TodoManagerBackup {
	
//	 Fields, getters and Setters
//	 ===========================
	
	private static Todo firstTodo;
	private static int counter = 0;
	private static int nextID = 0;
	private static final LocalDate DummyDate = LocalDate.of(6, 6, 6);
	

	private static Todo getFirstTodo() {
		return firstTodo;
	}

	private static void setFirstTodo(Todo firstTodo) {
		TodoManagerBackup.firstTodo = firstTodo;
	}

	private static int getCounter() {
		return counter;
	}

	private static void setCounter(int counter) {
		TodoManagerBackup.counter = counter;
	}
	
	private static int getNextID() {
		return nextID;
	}

	private static void setNextID(int nextID) {
		TodoManagerBackup.nextID = nextID;
	}

	private static LocalDate getDummyDate() {
		return DummyDate;
	}

//	public static void printAll() {
//		// debug only!
//		Todo currTodo = getFirstTodo();
//		while (currTodo != null) {
//			Out.println(currTodo);
//			currTodo = currTodo.nextTodo;
//		}
//	}
	
//	Actions on single Todos
//	=======================
	
	public static void newTodo(String descr, LocalDate dueDate) {
		Todo newTodo = new Todo(getNextID(), descr, dueDate);
		if (getFirstTodo() == null) {
			setFirstTodo(newTodo);
		} else {
			Todo prevTodo = null;
			Todo nextTodo = getFirstTodo();
			while (nextTodo.getDueDate().isBefore(newTodo.getDueDate())) {
				prevTodo = nextTodo;
				nextTodo = nextTodo.getNextTodo();
				if (nextTodo == null) {
					break;
				}
			}
			if (prevTodo == null) {
				setFirstTodo(newTodo);
			} else {
//				prevTodo.nextTodo = newTodo;
				prevTodo.setNextTodo(newTodo);
			}
			newTodo.setNextTodo(nextTodo);
//			newTodo.nextTodo = nextTodo;
		}
		setCounter(getCounter() + 1);
		setNextID(getNextID() + 1);
	}
	
	public static Todo getTodo(int id) {
		Todo result = null;
		Todo currTodo = getFirstTodo();
		while (currTodo != null) {
			if (currTodo.getId() == id) {
				result = currTodo;
				break;
			}
			currTodo = currTodo.getNextTodo();
		}
//		Todo result = currTodo;
		return result;
	}
	
	public static void markDone(int id) {
		Todo toMark = getTodo(id);
		if (toMark == null) {
			Out.println("error: " + id + " is not a valid Todo Id.");
		} else {
			toMark.setStatus(Status.Done);
		}
	}
	
	public static void delete(int id) {
		if (getFirstTodo() == null) {return;}
		else if (getFirstTodo().getId() == id) {
			setFirstTodo(getFirstTodo().getNextTodo());
			setCounter(getCounter()-1);
		} else {
			Todo currTodo = getFirstTodo();
			Todo prevTodo = null;
			while (currTodo != null && currTodo.getId() != id) {
				prevTodo = currTodo;
				currTodo = currTodo.getNextTodo();
			} 
			if (currTodo != null) {
				prevTodo.setNextTodo(currTodo.getNextTodo());
				setCounter(getCounter()-1);
			}
		}
	}
	
//	Actions on the entire list
//	==========================
	
	public static Todo[] getAll() {
		Todo[] result = new Todo[getCounter()];
		// Wegen dieser Zeile brauche ich für den Counter eine eigene Variable, die wieder herunterzaehlen kann.
		// Alternative: ein zweites Array mit der tatsaechlichen Anzahl machen, wie ich das bei den Suchlaeufen mache.
		// Ueberhaupt alles in die Suchfunktionen integrieren?
		Todo currTodo = getFirstTodo();
		while (currTodo != null) {
			int i = 0;
			while (result[i] != null) { i += 1; }
			result[i] = currTodo;
			currTodo = currTodo.getNextTodo();
		}
		return result;
	}
		
	private static Todo[] FilterBy(boolean stateSet, Status state, boolean dateSet, LocalDate date) {
		boolean[] axed = new boolean[getCounter()];
//		int selectionCounter = getCounter();
		int axedIndex = 0;
		Todo currTodo = getFirstTodo();
		while (currTodo != null) {
			if (stateSet) {
				if (state != currTodo.getStatus() ) {
					axed[axedIndex] = true;
				}
			}
			if (dateSet) {
				if (date.isBefore(currTodo.getDueDate())) {
					axed[axedIndex] = true;
				}
			}
			currTodo = currTodo.getNextTodo();
			axedIndex += 1;
		}
		int selectionCounter = 0;
		for (boolean i : axed) {
			if (i == false) { selectionCounter += 1; }
		}
		Todo[] result = new Todo[selectionCounter];
		int resultIndex = 0;
		axedIndex = 0;
		currTodo = getFirstTodo();
		while (currTodo != null) {
			if (axed[axedIndex] == false) {
				result[resultIndex] = currTodo;
				resultIndex += 1;
			}
			axedIndex += 1;
			currTodo = currTodo.getNextTodo();
		}
		return result;
	}

	public static Todo[] getAllOpen() {
		return FilterBy(true,Status.Open,false,getDummyDate());
	}
	public static Todo[] getAllDone() {
		return FilterBy(true,Status.Done,false,getDummyDate());
	}
	public static Todo[] getAllUntil(LocalDate date) {
		return FilterBy(false,Status.Open,true,date);
	}
	public static Todo[] getOpenUntil(LocalDate date) {
		return FilterBy(true,Status.Open,true,date);
	}
	public static Todo[] getDoneUntil(LocalDate date) {
		return FilterBy(true,Status.Done,true,date);
	}
	//and for sake of completion
	public static Todo[] getAllButFilteredButActuallyNot() {
		return FilterBy(false,Status.Done,false,getDummyDate());
	}
	
	

}
