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

        String[] tasks = new String[100];
        boolean[] isDone = new boolean[100];
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
                    String statusIcon = isDone[i] ? "X" : " ";
                    System.out.printf("%d.[%s] %s%n", i + 1, statusIcon, tasks[i]);
                }
                System.out.println(LINE);
            } else if (command.startsWith("mark ")) {
                try {
                    int taskIndex = Integer.parseInt(command.substring(5)) - 1;
                    if (taskIndex < 0 || taskIndex >= itemCount) {
                        System.out.println("Please enter a valid task number.");
                        continue;
                    }

                    isDone[taskIndex] = true;
                    System.out.println(LINE);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.printf("[X] %s%n", tasks[taskIndex]);
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

                    isDone[taskIndex] = false;
                    System.out.println(LINE);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.printf("[ ] %s%n", tasks[taskIndex]);
                    System.out.println(LINE);
                } catch (NumberFormatException exception) {
                    System.out.println("Please enter a valid task number.");
                }
            } else {
                if (itemCount < tasks.length) {
                    tasks[itemCount] = command;
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
