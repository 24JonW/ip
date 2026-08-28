package jonathan.parser;

import jonathan.command.AddCommand;
import jonathan.command.CheckCommand;
import jonathan.command.DeleteCommand;
import jonathan.command.ExitCommand;
import jonathan.command.Command;
import jonathan.command.ListCommand;
import jonathan.command.MarkCommand;
import jonathan.JonathanException;
import jonathan.task.ToDo;
import jonathan.task.Deadlines;
import jonathan.task.Event;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {

    public static Command parse(String fullCommand) throws jonathan.JonathanException {
        String command = fullCommand.trim();

        if (command.equals("bye")) {
            return new ExitCommand();
        } else if (command.equals("list")) {
            return new ListCommand();
        } else if (command.startsWith("mark ") || command.equals("mark")) {
            int index = extractIndex(command, "mark");
            return new MarkCommand(index, true);
        } else if (command.startsWith("unmark ") || command.equals("unmark")) {
            int index = extractIndex(command, "unmark");
            return new MarkCommand(index, false);
        } else if (command.startsWith("delete ") || command.equals("delete")) {
            int index = extractIndex(command, "delete");
            return new DeleteCommand(index);
        } else if (command.startsWith("check ") || command.equals("check")) {
            String dateString = command.substring("check".length()).trim();
            require(!dateString.isEmpty(), "Please provide a date to check (e.g., check 2026-08-27).");
            return new CheckCommand(dateString);
        } else if (command.startsWith("todo ") || command.equals("todo")) {
            String description = command.substring("todo".length()).trim();
            require(!description.isEmpty(), "A todo needs a description after `todo`.");
            return new AddCommand(new ToDo(description));
        } else if (command.startsWith("deadline ") || command.equals("deadline")) {
            String details = command.substring("deadline".length()).trim();
            int byIndex = details.indexOf(" /by ");
            require(byIndex > 0 && byIndex + 5 < details.length(), "A deadline needs a description and a `/by` time.");

            String description = details.substring(0, byIndex);
            String by = details.substring(byIndex + 5).trim();
            require(isValidDate(by), "The deadline date must be in yyyy-mm-dd format.");
            return new AddCommand(new Deadlines(description, by));
        } else if (command.startsWith("event ") || command.equals("event")) {
            String details = command.substring("event".length()).trim();
            int fromIndex = details.indexOf(" /from ");
            int toIndex = details.indexOf(" /to ");
            require(fromIndex > 0 && toIndex > fromIndex + 7 && toIndex + 5 < details.length(),
                    "An event needs a description, a `/from` time, and a `/to` time.");

            String description = details.substring(0, fromIndex);
            String from = details.substring(fromIndex + 7, toIndex).trim();
            String to = details.substring(toIndex + 5).trim();
            require(isValidDate(from) && isValidDate(to), "jonathan.task.Event dates must be in yyyy-mm-dd format.");
            return new AddCommand(new Event(description, from, to));
        } else {
            throw new JonathanException("I don't recognize that command. Try todo, deadline, event, list, mark, unmark, or bye.");
        }
    }

    private static int extractIndex(String command, String commandWord) throws JonathanException {
        String numberText = command.substring(commandWord.length()).trim();
        require(!numberText.isEmpty(), "`" + commandWord + "` needs a task number.");
        try {
            return Integer.parseInt(numberText) - 1; // Zero-based index
        } catch (NumberFormatException exception) {
            throw new JonathanException("`" + commandWord + "` needs a valid integer task number.");
        }
    }

    /** Throws an exception when a required command condition is not met. */
    public static void require(boolean condition, String message) throws JonathanException {
        if (!condition) {
            throw new JonathanException(message);
        }
    }

    public static boolean isValidDate(String dateString) {
        try {
            LocalDate.parse(dateString);
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        }
    }

}
