
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Deadlines extends Task {
//    private String by;
    LocalDate d1;

    public Deadlines(String description, String by) {
        super(description);
//        this.by = by;
        d1= LocalDate.parse(by);

    }

    @Override
    public boolean isOccuringOn(LocalDate date) {
        return d1 != null && d1.equals(date);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + d1.format(DateTimeFormatter.ofPattern("MMM d yyyy"))+ ")";
    }

    @Override
    public String toFileString() {
        return "D | " + getStatusCode() + " | " + getDescription() + " | "
                + d1.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }
}
