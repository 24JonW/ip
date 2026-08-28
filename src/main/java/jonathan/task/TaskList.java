package jonathan.task;

import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import jonathan.JonathanException;

/**
 * Represents and manages the in-memory list of tasks.
 * Provides operations to add, delete, retrieve, and track the size of the task list.
 */
public class TaskList {
    private Task[] tasks;
    private int itemCount;
    private static final int MAX_TASKS= 100;

    /**
     * Constructs an empty {@code TaskList} with a maximum capacity of 100 tasks.
     */
    public TaskList() {
        this.tasks= new Task[MAX_TASKS];
        this.itemCount = 0;
    }

    /**
     * Adds a new task to the list and increments the item count.
     *
     * @param task The {@code Task} to be added to the list.
     */
    public void AddTask(Task task) {
        this.tasks[itemCount] = task;
        this.itemCount++;
    }

    /**
     * Deletes the task at the specified index, shifts the remaining tasks to fill the gap,
     * and decrements the item count.
     *
     * @param index The zero-based index of the task to be removed.
     * @return The {@code Task} that was successfully removed from the list.
     */
    public Task deleteTask(int index) {
        Task removedTask= tasks[index];
        for (int i= index; i<itemCount-1; i++) {
            tasks[i]= tasks[i+1];
        }
        tasks[itemCount-1]= null;
        itemCount--;
        return removedTask;
    }

    /**
     * Retrieves the task located at the specified index without removing it.
     *
     * @param index The zero-based index of the desired task.
     * @return The {@code Task} at the specified index.
     */
    public Task getTask(int index) {
        return tasks[index];
    }

    /**
     * Gets the total number of active tasks currently stored in the list.
     *
     * @return The integer count of current tasks.
     */
    public int getSize() {
        return this.itemCount;
    }

    /**
     * Retrieves the underlying array containing all tasks.
     *
     * @return The array of {@code Task} objects.
     */
    public Task[] getAllTasks() {
        return this.tasks;
    }

    /**
     * Constructs a {@code TaskList} by parsing a list of saved text lines.
     * Rebuilds {@code ToDo}, {@code Deadline}, and {@code Event} tasks from the stored data.
     *
     * @param fileLines A list of raw strings from the save file, each representing a single task.
     * @throws JonathanException If an error occurs during task initialization.
     */
    public TaskList(List<String> fileLines) throws JonathanException {
        this.tasks = new Task[MAX_TASKS];
        this.itemCount = 0;

        for (String line : fileLines) {
            // Split by " | " - note the spaces around the pipe character!
            String[] parts = line.split(" \\| ");
            if (parts.length < 3) continue; // Skip malformed lines

            String type = parts[0];
            String status = parts[1];
            String description = parts[2];

            Task task = null;

            switch (type) {
                case "T":
                    task = new ToDo(description);
                    break;
                case "D":
                    if (parts.length >= 4) {
                        LocalDate parsedDate = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("MMM d yyyy"));
                        task = new Deadlines(description, parsedDate.toString());
                    }
                    break;
                case "E":
                    if (parts.length >= 5) {
                        LocalDate fromDate = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("MMM d yyyy"));
                        LocalDate toDate = LocalDate.parse(parts[4], DateTimeFormatter.ofPattern("MMM d yyyy"));
                        task = new Event(description, fromDate.toString(), toDate.toString());
                    }
                    break;
            }

            if (task != null) {
                if (status.equals("1")) {
                    task.markAsDone();
                }
                this.AddTask(task);
            }
        }
    }
}
