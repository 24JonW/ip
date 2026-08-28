import java.io.IOException;
import java.util.Scanner;


/**
 * A chatbot that stores tasks in memory and responds to simple commands.
 */
public class Jonathan {
    private TaskList tasklist;
    private Storage storage;
    private UI ui;

    public Jonathan(String filePath) {
        ui = new UI();
        storage= new Storage("data/jonathan.txt");
        try {
            tasklist= new TaskList(storage.load());
        } catch (IOException | JonathanException e) {
            ui.showError("Failed to load tasks from file. Starting with an empty list.");
            tasklist = new TaskList();
        }

    }
    public void run() {
        ui.showWelcome();
        boolean isExit= false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand(); // Get input
                Command c = Parser.parse(fullCommand); // Parse into a Command
                c.execute(tasklist, ui, storage);         // Execute the Command
                isExit = c.isExit();
            } catch (JonathanException | IOException e) {
                ui.showError(e.getMessage());
            }
        }

    }


    /**
     * Starts the chatbot.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new Jonathan("data/jonathan.txt").run();
    }
}
