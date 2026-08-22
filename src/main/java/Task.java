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
     * Returns this task in the format used by the chatbot.
     *
     * @return the status icon followed by the description
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}


