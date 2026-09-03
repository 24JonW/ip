package jonathan.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ToDoTest {

    @Test
    public void toString_validToDo_correctFormat() {
        ToDo todo = new ToDo("read book");
        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void toFileString_validToDo_correctFormat() {
        ToDo todo = new ToDo("read book");
        assertEquals("T | 0 | read book", todo.toFileString());
    }

    @Test
    public void toFileString_markedToDo_correctFormat() {
        ToDo todo = new ToDo("read book");
        todo.markAsDone();
        assertEquals("T | 1 | read book", todo.toFileString());
    }
}
