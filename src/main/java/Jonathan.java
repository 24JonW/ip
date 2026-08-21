import java.util.Scanner;

public class Jonathan {
    private static final String LINES=  "____________________________________________________________";
    public static void main(String[] args) {
        String banner = "     _             _   _                 \n"
                + "    | | ___  _ __ |  ___  |_| |__   __ _ _ __  \n"
                + " _  | |/ _ \\| '_ \\/   \\| '_ \\ / _` | '_ \\ \n"
                + "| |_| | (_) | | | | | (_) | | | | (_| | | | |\n"
                + " \\___/ \\___/|_| |_\\ _/ \\__|_| |_|\\__,_|_| |_|\n";
        System.out.println(banner);

        String[] currentList= new String[100];
        int itemCount= 0;


        System.out.println(LINES);
        System.out.println("Hello! I am Jonathan");
        System.out.println("How may i help you?");
        System.out.println(LINES);
        Scanner scanner= new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();
            if (command.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(LINES);
                break;
            } else if (command.equals("list")) {
                for (int i=0; i<itemCount; i++) {
                    System.out.printf("%d. %s\n", (i+1), currentList[i]);
                }
                System.out.println(LINES);
            } else {
                if (itemCount < currentList.length) {
                    currentList[itemCount]= command;
                    System.out.println(LINES);
                    System.out.printf("added: %s\n", command);
                    System.out.println(LINES);
                    itemCount++;
                } else {
                    System.out.println(LINES);
                    System.out.println("List is Full!");
                    System.out.println(LINES);
                }

            }
        }
    }
}
