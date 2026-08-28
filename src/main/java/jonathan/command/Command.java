package jonathan.command;

import java.io.IOException;
import jonathan.storage.Storage;
import jonathan.task.TaskList;
import jonathan.ui.UI;
import jonathan.JonathanException;



public abstract class Command {
    /** Executes the command using the provided dependencies. */
    public abstract void execute(TaskList tasks, UI ui, Storage storage) throws JonathanException, IOException;

    /** Returns true only for the jonathan.command.ExitCommand to break the main loop. */
    public boolean isExit() {
        return false;
    }
}
