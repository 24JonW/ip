import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Handles all console input and output formatting for the chatbot.
 */
public class UI {
    private static final String BANNER = "     _             _   _\n"
            + "    | | ___  _ __ | |_| |__   __ _ _ __\n"
            + " _  | |/ _ \\| '_ \\| __| '_ \\ / _` | '_ \\\n"
            + "| |_| | (_) | | | | |_| | | | (_| | | | |\n"
            + " \\___/ \\___/|_| |_|\\__|_| |_|\\__,_|_| |_|\n";
    private static final String LINE = "____________________________________________________________";

    /** Displays the chatbot banner and welcome message. */
    public void showWelcome() {
        System.out.println(BANNER);
        System.out.println(LINE);
        System.out.println("Hello! I'm Jonathan.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    /** Displays the farewell message. */
    public void showGoodbye() {
        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    /** Displays all tasks in the task list. */
    public void showTaskList(Task[] tasks, int itemCount) {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < itemCount; i++) {
            System.out.printf("%d.%s%n", i + 1, tasks[i]);
        }
        System.out.println(LINE);
    }

    /** Displays confirmation after marking a task as done. */
    public void showMarked(Task task) {
        System.out.println(LINE);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
        System.out.println(LINE);
    }

    /** Displays confirmation after marking a task as not done. */
    public void showUnmarked(Task task) {
        System.out.println(LINE);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
        System.out.println(LINE);
    }

    /** Displays a consistently formatted error message. */
    public void showError(String message) {
        System.out.println(LINE);
        System.out.println("Error: " + message);
        System.out.println(LINE);
    }

    /** Displays confirmation after successfully adding a task. */
    public void showAdded(Task task, int itemCount) {
        System.out.println(LINE);
        System.out.println("Got it! I added this task:");
        System.out.println("  " + task);
        System.out.printf("Now you have %d tasks in the list.%n", itemCount);
        System.out.println(LINE);
    }

    /** Displays confirmation after successfully deleting a task. */
    public void showDeleted(Task task, int itemCount) {
        System.out.println(LINE);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.printf("Now you have %d tasks in the list.%n", itemCount);
        System.out.println(LINE);
    }

    /** Displays all tasks occurring on the requested date. */
    public void showActivitiesOn(String dateString, Task[] tasks, int itemCount) {
        try {
            LocalDate targetDate = LocalDate.parse(dateString);
            System.out.println(LINE);
            System.out.println("Here are the tasks occurring on "
                    + targetDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ":");

            int matchCount = 0;
            for (int i = 0; i < itemCount; i++) {
                if (tasks[i].isOccuringOn(targetDate)) {
                    matchCount++;
                    System.out.printf("%d.%s%n", matchCount, tasks[i]);
                }
            }

            if (matchCount == 0) {
                System.out.println("  No tasks found for this date.");
            }
            System.out.println(LINE);
        } catch (DateTimeParseException exception) {
            showError("Invalid date format. Please use yyyy-mm-dd (e.g., 2026-08-27).");
        }
    }
}
