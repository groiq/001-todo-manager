package todos.backend;

import java.time.LocalDate;
import inout.Out;

public class TodoManager {
	
//	===========================
//	Fields, Getters and Setters
//	===========================
	
	private static Todo firstTodo;
	private static int counter = 0;
	private static final LocalDate dummyDate = LocalDate.of(6, 6, 6);
	
	private static Todo getFirstTodo() {
		return firstTodo;
	}
	private static void setFirstTodo(Todo firstTodo) {
		TodoManager.firstTodo = firstTodo;
	}
	private static int getCounter() {
		return counter;
	}
	private static void setCounter(int counter) {
		TodoManager.counter = counter;
	}
	private static LocalDate getDummyDate() {
		return dummyDate;
	}

//	=======================
//	Actions on single Todos
//	=======================

	public static void newTodo(String descr, LocalDate dueDate) {
		Todo newTodo = new Todo(getCounter(), descr, dueDate);
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
				prevTodo.setNextTodo(newTodo);
			}
			newTodo.setNextTodo(nextTodo);
		}
		setCounter(getCounter() + 1);
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
		if (getFirstTodo() == null) {
			Out.println("error: Todo list is empty.");
			return;
		} else if (getFirstTodo().getId() == id) {
			setFirstTodo(getFirstTodo().getNextTodo());
		} else {
			Todo currTodo = getFirstTodo();
			Todo prevTodo = null;
			while (currTodo != null && currTodo.getId() != id) {
				prevTodo = currTodo;
				currTodo = currTodo.getNextTodo();
			}
			if (currTodo != null) {
				prevTodo.setNextTodo(currTodo.getNextTodo());
			} else {
				Out.println("error: " + id + " is not a valid Todo Id.");
			}
		}
	}

	
//	==========================
//	Actions on the entire list
//	==========================
	
	private static Todo[] getBy(boolean stateSet, Status state, boolean dateSet, LocalDate date) {
		boolean[] axed = new boolean[getCounter()];
		int axedIndex = 0;
//		int selectionCounter = getCounter();
		int axedCounter = 0;
		Todo currTodo = getFirstTodo();
		while (currTodo != null) {
			if (stateSet) {
				if (state != currTodo.getStatus() ) {
					if (axed[axedIndex] == false) {
						axedCounter += 1;
					}
					axed[axedIndex] = true;
				}
			}
			if (dateSet) {
				if (date.isBefore(currTodo.getDueDate())) {
					if (axed[axedIndex] == false) {
						axedCounter += 1;
					}
					axed[axedIndex] = true;
				}
			}
			currTodo = currTodo.getNextTodo();
			axedIndex += 1;
		}
		int selectionCounter = axedIndex - axedCounter;
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
		return getBy(true,Status.Open,false,getDummyDate());
	}
	public static Todo[] getAllDone() {
		return getBy(true,Status.Done,false,getDummyDate());
	}
	public static Todo[] getAllUntil(LocalDate date) {
		return getBy(false,Status.Open,true,date);
	}
	public static Todo[] getOpenUntil(LocalDate date) {
		return getBy(true,Status.Open,true,date);
	}
	public static Todo[] getDoneUntil(LocalDate date) {
		return getBy(true,Status.Done,true,date);
	}
	public static Todo[] getAll() {
		return getBy(false,Status.Done,false,getDummyDate());
	}
	
	public static void deleteDoneUnto(LocalDate date) {
		if (getFirstTodo() != null) {
			Todo prevTodo;
			Todo currTodo = getFirstTodo();
			while (currTodo.getStatus() == Status.Done && !currTodo.getDueDate().isAfter(date)) {
				currTodo = currTodo.getNextTodo();
			}
			setFirstTodo(currTodo);
			prevTodo = currTodo;
			currTodo = currTodo.getNextTodo();
			while (currTodo != null && !currTodo.getDueDate().isAfter(date)) {
//				Out.println(currTodo);
				if (currTodo.getStatus() == Status.Done) {
					prevTodo.setNextTodo(currTodo.getNextTodo());
				} else {
					prevTodo = currTodo;
				}
//				prevTodo = currTodo;
				currTodo = currTodo.getNextTodo();
			}
		}
	}
	
	


//	Anmerkungen
//	===========
	
	
	//  later: write decent docstrings.
	
	// For later: Instead of the firstTodo field, have some kind of dummy object that is not found in the list
	// but that has the actual first Todo as a field complete with getters and setters.
	// So instead of having extra code for setting the firstTodo I can just use the "setNextTodo" commands.
	
	// Instead of calling getNextTodo() from the Todo class, define your own getNextTodo(Todo currTodo) method that first checks
	// whether currTodo isn't null.
	// Make currTodo a field? Then I could make getNextTodo() parameterless...
	// But wait, does any of this solve the firstTodo problem?
	
	// Merge the delete methods into one; delete by condition (in one case, by id, in other, by state + date)
	
	// reorganize code: Fields+Getters, C,R,U,D
	
	// Alle Methoden ausser Create kann ich auf ein einzelnes Todo anwenden oder auf eine Auswahlliste.
	// Dh Auswahl nach ID oder Auswahl nach Kriterium.
	
	// Das heißt im Prinzip, daß sämtliche Methoden sich eine Liste von Todos mit getBy() holen und dann darauf operieren würden.
	// Halt - Delete geht nicht einfach so mit getBy(), weil ich nicht nur das Todo brauche, sondern auch seinen Vorgänger.
	// Workaround: Ich gebe nicht die zu löschenden Todos zurück, sondern die Vorgänger. Wenn ich das erst löschen soll, gebe ich
	// das letzte zurück.
	// Allerdings ist getBy() nicht darauf ausgelegt, daß ich ein Todo hineinnehme, wenn sein *Nachfolger* ein Kriterium erfüllt.
	
	// eine Methode getBy(), eine Methode deleteBy(); alle Read-/Update-Methoden verwenden GetBy().
	
	// I guess what I need is a yield() statement - have a method that works through the Todo list and yields the current Todo..
	// and, if needed, the previous one, too
	
	// Fleissaufgabe: Alle dueDates um einen bestimmten Zeitraum verschieben.
	
	
}