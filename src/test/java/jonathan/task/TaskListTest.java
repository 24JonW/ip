package jonathan.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void addTask_validTask_increasesSize() {
        TaskList taskList = new TaskList();
        ToDo todo = new ToDo("read book");

        taskList.addTask(todo);

        assertEquals(1, taskList.getSize());
        assertSame(todo, taskList.getTask(0));
    }

    @Test
    public void deleteTask_existingTask_removesTask() {
        TaskList taskList = new TaskList();
        ToDo first = new ToDo("first");
        ToDo second = new ToDo("second");

        taskList.addTask(first);
        taskList.addTask(second);

        Task deleted = taskList.deleteTask(0);

        assertSame(first, deleted);
        assertEquals(1, taskList.getSize());
        assertSame(second, taskList.getTask(0));
    }

    @Test
    public void emptyTaskList_getTask_throwsAssertionError() {
        TaskList taskList = new TaskList();

        assertThrows(AssertionError.class, () -> {
            taskList.getTask(0);
        });
    }
}
