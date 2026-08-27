import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
