import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * A chatbot that stores tasks in memory and responds to simple commands.
 */
public class Jonathan {
    private static final String LINE = "____________________________________________________________";
    private static final int MAX_TASKS = 100;
    private static final Path SAVE_PATH = Path.of("data", "jonathan.txt");

    /**
     * Starts the chatbot.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) throws IOException {
        String banner = "     _             _   _\n"
                + "    | | ___  _ __ | |_| |__   __ _ _ __\n"
                + " _  | |/ _ \\| '_ \\| __| '_ \\ / _` | '_ \\\n"
                + "| |_| | (_) | | | | |_| | | | (_| | | | |\n"
                + " \\___/ \\___/|_| |_|\\__|_| |_|\\__,_|_| |_|\n";
        System.out.println(banner);

        Task[] tasks = new Task[MAX_TASKS];
        int itemCount = 0;

        System.out.println(LINE);
        System.out.println("Hello! I'm Jonathan.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();

            if (command.equals("bye")) {
                System.out.println(LINE);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            } else if (command.equals("list")) {
                printList(tasks, itemCount);
            } else if (command.equals("mark") || command.startsWith("mark ")) {
                try {
                    int taskIndex = parseTaskIndex(command, "mark", itemCount);
                    tasks[taskIndex].markAsDone();
                    saveTasks(tasks, itemCount);
                    System.out.println(LINE);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks[taskIndex]);
                    System.out.println(LINE);
                } catch (JonathanException exception) {
                    printError(exception.getMessage());
                }
            } else if (command.equals("unmark") || command.startsWith("unmark ")) {
                try {
                    int taskIndex = parseTaskIndex(command, "unmark", itemCount);
                    tasks[taskIndex].markAsNotDone();
                    saveTasks(tasks, itemCount);
                    System.out.println(LINE);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks[taskIndex]);
                    System.out.println(LINE);
                } catch (JonathanException exception) {
                    printError(exception.getMessage());
                }
            } else if (command.equals("todo") || command.startsWith("todo ")) {
                try {
                    String description = command.substring("todo".length()).trim();
                    require(!description.isEmpty(), "A todo needs a description after `todo`.");
                    require(itemCount < MAX_TASKS, "The task list is full.");
                    tasks[itemCount] = new ToDo(description);
                    itemCount++;
                    saveTasks(tasks, itemCount);
                    printAddedMessage(tasks[itemCount - 1], itemCount);
                } catch (JonathanException exception) {
                    printError(exception.getMessage());
                }
            } else if (command.equals("deadline") || command.startsWith("deadline ")) {
                try {
                    String details = command.substring("deadline".length()).trim();
                    int byIndex = details.indexOf(" /by ");
                    require(byIndex > 0 && byIndex + 5 < details.length(),
                            "A deadline needs a description and a `/by` time.");
                    require(itemCount < MAX_TASKS, "The task list is full.");
                    String description = details.substring(0, byIndex);
                    String by = details.substring(byIndex + 5);
                    tasks[itemCount] = new Deadlines(description, by);
                    itemCount++;
                    saveTasks(tasks, itemCount);
                    printAddedMessage(tasks[itemCount - 1], itemCount);
                } catch (JonathanException exception) {
                    printError(exception.getMessage());
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
                    String from = details.substring(fromIndex + 7, toIndex);
                    String to = details.substring(toIndex + 5);
                    tasks[itemCount] = new Event(description, from, to);
                    itemCount++;
                    saveTasks(tasks, itemCount);
                    printAddedMessage(tasks[itemCount - 1], itemCount);
                } catch (JonathanException exception) {
                    printError(exception.getMessage());
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
                    printDeletedMessage(removedTask, itemCount);

                } catch (JonathanException exception) {
                    printError(exception.getMessage());
                }
            } else {
                printError("I don't recognize that command. Try todo, deadline, event, list, mark, unmark, or bye.");
            }
        }
    }

    /** Prints all stored tasks with their current status. */
    private static void printList(Task[] tasks, int itemCount) {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < itemCount; i++) {
            System.out.printf("%d.%s%n", i + 1, tasks[i]);
        }
        System.out.println(LINE);
    }

    /** Prints a consistently formatted error message. */
    private static void printError(String message) {
        System.out.println(LINE);
        System.out.println("Error: " + message);
        System.out.println(LINE);
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

    /** Prints confirmation after successfully adding a task. */
    private static void printAddedMessage(Task task, int itemCount) {
        System.out.println(LINE);
        System.out.println("Got it! I added this task:");
        System.out.println("  " + task);
        System.out.printf("Now you have %d tasks in the list.%n", itemCount);
        System.out.println(LINE);
    }
    private static void printDeletedMessage(Task task, int itemCount) {
        System.out.println(LINE);
        System.out.println("Noted. I've removed this task: ");
        System.out.println("  " + task);
        System.out.printf("Now you have %d tasks in the list.%n", itemCount);
        System.out.println(LINE);

    }
}
