import jonathan.task.Task;

import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class TaskList {
    private Task[] tasks;
    private int itemCount;
    private static final int MAX_TASKS= 100;

    public TaskList() {
        this.tasks= new Task[MAX_TASKS];
        this.itemCount = 0;
    }

    public void AddTask(Task task) {
        this.tasks[itemCount] = task;
        this.itemCount++;
    }

    public Task deleteTask(int index) {
        Task removedTask= tasks[index];
        for (int i= index; i<itemCount-1; i++) {
            tasks[i]= tasks[i+1];
        }
        tasks[itemCount-1]= null;
        itemCount--;
        return removedTask;
    }

    public Task getTask(int index) {
        return tasks[index];
    }

    public int getSize() {
        return this.itemCount;
    }
    public Task[] getAllTasks() {
        return this.tasks;
    }

    /** 2nd contructor **/
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
