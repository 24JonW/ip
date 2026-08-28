package jonathan.task;

import java.time.LocalDate;

/**
 * Represents one task in the chatbot's in-memory task list.
 */
public class Task {
    private final String description;
//    private boolean isDone;
    private TaskStatus status = TaskStatus.NOT_DONE;

    public Task(String description) {
        this.description = description;
//        this.isDone = false;
    }

    /** Marks this task as done. */
    public void markAsDone() {
//        isDone = true;
        status= TaskStatus.DONE;
    }

    /** Marks this task as not done. */
    public void markAsNotDone() {
        status= TaskStatus.NOT_DONE;
//        isDone = false;
    }

    /**
     * Returns the icon used to show this task's completion status.
     *
     * @return {@code X} when done; otherwise a blank space
     */
    public String getStatusIcon() {
        return status.getIcon();
//        return isDone ? "X" : " ";
    }

    /**
     * Returns this task in the format used to save it to disk.
     *
     * @return one line containing the task type, status, and description
     */
    public String toFileString() {
        return "T | " + getStatusCode() + " | " + description;
    }
    /** Returns whether the task falls on a particular date. **/
    public boolean isOccuringOn(LocalDate date) {
        return false;
    }


    /** Returns the description for task subtypes that need to save it. */
    protected String getDescription() {
        return description;
    }

    /** Returns {@code 1} when complete and {@code 0} otherwise. */
    protected String getStatusCode() {
        return status == TaskStatus.DONE ? "1" : "0";
    }

    /**
     * Returns this task in the format used by the chatbot.
     *
     * @return the status icon followed by the description
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
