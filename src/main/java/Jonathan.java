import java.util.Scanner;

/**
 * A chatbot that stores tasks in memory and responds to simple commands.
 */
public class Jonathan {
    private static final String LINE = "____________________________________________________________";

    /**
     * Starts the chatbot.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        String banner = "     _             _   _                 \n"
                + "    | | ___  _ __ | |_| |__   __ _ _ __  \n"
                + " _  | |/ _ \\| '_ \\| __| '_ \\ / _` | '_ \\ \n"
                + "| |_| | (_) | | | | |_| | | | (_| | | | |\n"
                + " \\___/ \\___/|_| |_|\\__|_| |_|\\__,_|_| |_|\n";
        System.out.println(banner);

        Task[] tasks = new Task[100];
        int itemCount = 0;

        System.out.println(LINE);
        System.out.println("Hello! I'm Jonathan.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();

            if (command.equals("bye")) {
                System.out.println(LINE);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            } else if (command.equals("list")) {
                System.out.println(LINE);
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < itemCount; i++) {
                    System.out.printf("%d.%s%n", i + 1, tasks[i]);
                }
                System.out.println(LINE);
            } else if (command.startsWith("mark ")) {
                try {
                    int taskIndex = Integer.parseInt(command.substring(5)) - 1;
                    if (taskIndex < 0 || taskIndex >= itemCount) {
                        System.out.println("Please enter a valid task number.");
                        continue;
                    }

                    tasks[taskIndex].markAsDone();
                    System.out.println(LINE);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks[taskIndex]);
                    System.out.println(LINE);
                } catch (NumberFormatException exception) {
                    System.out.println("Please enter a valid task number.");
                }
            } else if (command.startsWith("unmark ")) {
                try {
                    int taskIndex = Integer.parseInt(command.substring(7)) - 1;
                    if (taskIndex < 0 || taskIndex >= itemCount) {
                        System.out.println("Please enter a valid task number.");
                        continue;
                    }

                    tasks[taskIndex].markAsNotDone();
                    System.out.println(LINE);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks[taskIndex]);
                    System.out.println(LINE);
                } catch (NumberFormatException exception) {
                    System.out.println("Please enter a valid task number.");
                }
            } else {
                if (itemCount < tasks.length) {
                    tasks[itemCount] = new Task(command);
                    System.out.println(LINE);
                    System.out.printf("added: %s%n", command);
                    System.out.println(LINE);
                    itemCount++;
                } else {
                    System.out.println(LINE);
                    System.out.println("List is Full!");
                    System.out.println(LINE);
                }
            }
        }
    }
}
