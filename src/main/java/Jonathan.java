import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * A chatbot that stores tasks in memory and responds to simple commands.
 */
public class Jonathan {
    private static final int MAX_TASKS = 100;
    private static final Path SAVE_PATH = Path.of("data", "jonathan.txt");

    /**
     * Starts the chatbot.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) throws IOException {
        Task[] tasks = new Task[MAX_TASKS];
        int itemCount = 0;
        UI ui = new UI();

        ui.showWelcome();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();

            if (command.equals("bye")) {
                ui.showGoodbye();
                break;
            } else if (command.equals("list")) {
                ui.showTaskList(tasks, itemCount);
            } else if (command.equals("mark") || command.startsWith("mark ")) {
                try {
                    int taskIndex = parseTaskIndex(command, "mark", itemCount);
                    tasks[taskIndex].markAsDone();
                    saveTasks(tasks, itemCount);
                    ui.showMarked(tasks[taskIndex]);
                } catch (JonathanException exception) {
                    ui.showError(exception.getMessage());
                }
            } else if (command.equals("unmark") || command.startsWith("unmark ")) {
                try {
                    int taskIndex = parseTaskIndex(command, "unmark", itemCount);
                    tasks[taskIndex].markAsNotDone();
                    saveTasks(tasks, itemCount);
                    ui.showUnmarked(tasks[taskIndex]);
                } catch (JonathanException exception) {
                    ui.showError(exception.getMessage());
                }
            } else if (command.equals("todo") || command.startsWith("todo ")) {
                try {
                    String description = command.substring("todo".length()).trim();
                    require(!description.isEmpty(), "A todo needs a description after `todo`.");
                    require(itemCount < MAX_TASKS, "The task list is full.");
                    tasks[itemCount] = new ToDo(description);
                    itemCount++;
                    saveTasks(tasks, itemCount);
                    ui.showAdded(tasks[itemCount - 1], itemCount);
                } catch (JonathanException exception) {
                    ui.showError(exception.getMessage());
                }
            } else if (command.equals("deadline") || command.startsWith("deadline ")) {
                try {
                    String details = command.substring("deadline".length()).trim();
                    int byIndex = details.indexOf(" /by ");
                    require(byIndex > 0 && byIndex + 5 < details.length(),
                            "A deadline needs a description and a `/by` time.");
                    require(itemCount < MAX_TASKS, "The task list is full.");
                    String description = details.substring(0, byIndex);
                    String by = details.substring(byIndex + 5).trim();
                    // Arrest invalid date formats
                    require(isValidDate(by), "The deadline date must be in yyyy-mm-dd format (e.g., 2026-08-27).");

                    tasks[itemCount] = new Deadlines(description, by);
                    itemCount++;
                    saveTasks(tasks, itemCount);
                    ui.showAdded(tasks[itemCount - 1], itemCount);
                } catch (JonathanException exception) {
                    ui.showError(exception.getMessage());
                }
            } else if (command.equals("event") || command.startsWith("event ")) {
                try {
                    String details = command.substring("event".length()).trim();
                    int fromIndex = details.indexOf(" /from ");
                    int toIndex = details.indexOf(" /to ");
                    require(fromIndex > 0 && toIndex > fromIndex + 7 && toIndex + 5 < details.length(),
                            "An event needs a description, a `/from` time, and a `/to` time.");
                    require(itemCount < MAX_TASKS, "The task list is full.");
                    String description = details.substring(0, fromIndex);
                    String from = details.substring(fromIndex + 7, toIndex).trim();
                    String to = details.substring(toIndex + 5).trim();

                    // Arrest invalid date formats
                    require(isValidDate(from) && isValidDate(to),
                            "Event dates must be in yyyy-mm-dd format (e.g., 2026-08-27).");

                    tasks[itemCount] = new Event(description, from, to);
                    itemCount++;
                    saveTasks(tasks, itemCount);
                    ui.showAdded(tasks[itemCount - 1], itemCount);
                } catch (JonathanException exception) {
                    ui.showError(exception.getMessage());
                }
            } else if (command.startsWith("delete ")) {
                try {
                    int taskIndex= parseTaskIndex(command, "delete", itemCount);
                    Task removedTask= tasks[taskIndex];
                    for (int i= taskIndex; i<itemCount-1; i++) {
                        tasks[i]= tasks[i+1];
                    }
                    tasks[itemCount-1]= null;
                    itemCount--;
                    saveTasks(tasks, itemCount);
                    ui.showDeleted(removedTask, itemCount);

                } catch (JonathanException exception) {
                    ui.showError(exception.getMessage());
                }
            } else if (command.startsWith("check ")) {
                try {
                    String dateString= command.substring("check".length()).trim();
                    require(!dateString.isEmpty(),"Please provide a date to check (e.g., check 2026-08-27)." );
                    ui.showActivitiesOn(dateString, tasks, itemCount);

                } catch (JonathanException exception) {
                    ui.showError(exception.getMessage());
                }

            } else {
                ui.showError("I don't recognize that command. Try todo, deadline, event, list, mark, unmark, or bye.");
            }
        }
    }

    /**
     * Parses and validates the one-based task number in a mark or unmark command.
     *
     * @param command full user command
     * @param commandWord either {@code mark} or {@code unmark}
     * @param itemCount number of stored tasks
     * @return the corresponding zero-based task index
     * @throws JonathanException if the task number is absent, invalid, or unavailable
     */
    private static int parseTaskIndex(String command, String commandWord, int itemCount)
            throws JonathanException {
        String numberText = command.substring(commandWord.length()).trim();
        try {
            require(itemCount > 0, "There are no tasks to " + commandWord + ".");
            int taskIndex = Integer.parseInt(numberText) - 1;
            require(taskIndex >= 0 && taskIndex < itemCount,
                    "Choose a task number from 1 to " + itemCount + ".");
            return taskIndex;
        } catch (NumberFormatException exception) {
            throw new JonathanException("`" + commandWord + "` needs a valid task number.");
        }
    }

    /** Throws an exception when a required command condition is not met. */
    private static void require(boolean condition, String message) throws JonathanException {
        if (!condition) {
            throw new JonathanException(message);
        }
    }

    /** Writes the current task list to the hard-coded data file. */
    private static void saveTasks(Task[] tasks, int itemCount) throws IOException {
        Files.createDirectories(SAVE_PATH.getParent());

        StringBuilder fileContents = new StringBuilder();
        for (int i = 0; i < itemCount; i++) {
            fileContents.append(tasks[i].toFileString()).append(System.lineSeparator());
        }
        Files.writeString(SAVE_PATH, fileContents.toString(), StandardCharsets.UTF_8);
    }

    private static boolean isValidDate(String dateString) {
        try {
            LocalDate.parse(dateString);
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        }
    }

}
