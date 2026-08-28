package jonathan.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific deadline.
 */
public class Deadlines extends Task {
//    private String by;
    LocalDate d1;

    /**
     * Constructs a {@code Deadlines} task with a description and a due date.
     * The date string must be in a format parseable by {@code LocalDate} (e.g., yyyy-mm-dd).
     *
     * @param description The details of the task.
     * @param by          The deadline date of the task as a string.
     */
    public Deadlines(String description, String by) {
        super(description);
//        this.by = by;
        d1= LocalDate.parse(by);

    }

    /**
     * Checks if the task's deadline falls exactly on the specified date.
     *
     * @param date The target date to check against the deadline.
     * @return {@code true} if the given date matches the deadline, {@code false} otherwise.
     */
    @Override
    public boolean isOccuringOn(LocalDate date) {
        return d1 != null && d1.equals(date);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + d1.format(DateTimeFormatter.ofPattern("MMM d yyyy"))+ ")";
    }

    /**
     * Returns the string representation of the deadline task for saving to the local data file.
     *
     * @return A formatted string delimited by pipes ("|") containing the task's save state.
     */
    @Override
    public String toFileString() {
        return "D | " + getStatusCode() + " | " + getDescription() + " | "
                + d1.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }
}
