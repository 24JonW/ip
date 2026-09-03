package jonathan.ui;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import jonathan.task.Task;

/**
 * Handles all console input and output formatting for the chatbot.
 */
public class UI {
    private static final String BANNER = "     _             _   _\n"
            + "    | | ___  _ __ | |_| |__   __ _ _ __\n"
            + " _  | |/ _ \\| '_ \\| __| '_ \\ / _` | '_ \\\n"
            + "| |_| | (_) | | | | |_| | | | (_| | | | |\n"
            + " \\___/ \\___/|_| |_|\\__|_| |_|\\__,_|_| |_|\n";
    private static final String LINE = "_____________________________________";
    private final Scanner scanner;
    private final PrintStream output;

    public UI() {
//        this.scanner = new Scanner(System.in);
        this(System.out);
    }
    public UI(PrintStream output) {
        this.scanner = new Scanner(System.in);
        this.output = output;
    }

    /** Displays the chatbot banner and welcome message. */
    public void showWelcome() {
        output.println(BANNER);
        output.println(LINE);
        output.println("Hello! I'm jonathan.Jonathan.");
        output.println("What can I do for you?");
        output.println(LINE);
    }

    /** Displays the farewell message. */
    public void showGoodbye() {
        output.println(LINE);
        output.println("Bye. Hope to see you again soon!");
        output.println(LINE);
    }

    /**
     * Displays all current tasks in the user's task list.
     *
     * @param tasks     The array containing the tasks to be displayed.
     * @param itemCount The number of active tasks currently in the array.
     */
    public void showTaskList(Task[] tasks, int itemCount) {
        output.println(LINE);
        output.println("Here are the tasks in your list:");
        for (int i = 0; i < itemCount; i++) {
            output.printf("%d.%s%n", i + 1, tasks[i]);
        }
        output.println(LINE);
    }

    /**
     * Displays a confirmation message after successfully marking a task as done.
     *
     * @param task The task that was marked as done.
     */
    public void showMarked(Task task) {
        output.println(LINE);
        output.println("Nice! I've marked this task as done:");
        output.println(task);
        output.println(LINE);
    }

    /**
     * Displays a confirmation message after successfully marking a task as not done.
     *
     * @param task The task that was marked as not done.
     */
    public void showUnmarked(Task task) {
        output.println(LINE);
        output.println("OK, I've marked this task as not done yet:");
        output.println(task);
        output.println(LINE);
    }

    /**
     * Displays a consistently formatted error message to the console.
     *
     * @param message The specific error explanation to be shown to the user.
     */
    public void showError(String message) {
        output.println(LINE);
        output.println("Error: " + message);
        output.println(LINE);
    }

    /**
     * Displays a confirmation message after successfully adding a new task.
     *
     * @param task      The task that was just added.
     * @param itemCount The updated total number of tasks in the list.
     */
    public void showAdded(Task task, int itemCount) {
        output.println(LINE);
        output.println("Got it! I added this task:");
        output.println("  " + task);
        output.printf("Now you have %d tasks in the list.%n", itemCount);
        output.println(LINE);
    }

    /**
     * Displays a confirmation message after successfully deleting a task.
     *
     * @param task      The task that was removed from the list.
     * @param itemCount The updated total number of tasks remaining in the list.
     */
    public void showDeleted(Task task, int itemCount) {
        output.println(LINE);
        output.println("Noted. I've removed this task:");
        output.println("  " + task);
        output.printf("Now you have %d tasks in the list.%n", itemCount);
        output.println(LINE);
    }

    /**
     * Searches for and displays all tasks that occur on a specific date.
     *
     * @param dateString The target date to search for, in {@code yyyy-mm-dd} format.
     * @param tasks      The array containing the user's tasks.
     * @param itemCount  The number of active tasks currently in the array.
     */
    public void showActivitiesOn(String dateString, Task[] tasks, int itemCount) {
        try {
            LocalDate targetDate = LocalDate.parse(dateString);
            output.println(LINE);
            output.println("Here are the tasks occurring on "
                    + targetDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ":");

            int matchCount = 0;
            for (int i = 0; i < itemCount; i++) {
                if (tasks[i].isOccuringOn(targetDate)) {
                    matchCount++;
                    output.printf("%d.%s%n", matchCount, tasks[i]);
                }
            }

            if (matchCount == 0) {
                output.println("  No tasks found for this date.");
            }
            output.println(LINE);
        } catch (DateTimeParseException exception) {
            showError("Invalid date format. Please use yyyy-mm-dd (e.g., 2026-08-27).");
        }
    }

    /**
     * Searches for and displays all tasks that contain the specified keyword in their description.
     *
     * @param keyword   The search term provided by the user.
     * @param tasks     The array containing the user's tasks.
     * @param itemCount The number of active tasks currently in the array.
     */
    public void showFoundTasks(String keyword, Task[] tasks, int itemCount) {
        output.println(LINE);
        output.println("Here are the matching tasks in your list:");

        int matchCount = 0;
        for (int i = 0; i < itemCount; i++) {
            if (tasks[i].toString().contains(keyword)) {
                matchCount++;
                output.printf("%d.%s%n", matchCount, tasks[i]);
            }
        }

        if (matchCount == 0) {
            output.println("  No matching tasks found.");
        }
        output.println(LINE);
    }

    /**
     * Reads the next line of user input from the console.
     *
     * @return The raw command string entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }
}
