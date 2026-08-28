import jonathan.task.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
//    private String from;
//    private String to;
    private LocalDate from;
    private LocalDate to;

    public Event(String description, String from, String to) {
        super(description);
//        this.from= from;
//        this.to= to;
        this.from = LocalDate.parse(from);
        this.to= LocalDate.parse(to);
    }

    @Override
    public boolean isOccuringOn(LocalDate date) {
        if (this.from == null || this.to == null || date == null) {
            return false;
        }
        // Returns true if the date is exactly on or after 'from', AND exactly on or before 'to'
        return !date.isBefore(this.from) && !date.isAfter(this.to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " to: " + this.to.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    @Override
    public String toFileString() {
        return "E | " + getStatusCode() + " | " + getDescription() + " | " + from.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " | " + to.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }
}
