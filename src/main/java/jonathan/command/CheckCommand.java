package jonathan.command;

import java.io.IOException;
import jonathan.task.Task;
import jonathan.storage.Storage;
import jonathan.task.TaskList;
import jonathan.ui.UI;
import jonathan.parser.Parser;
import jonathan.JonathanException;



public class CheckCommand extends Command {
    private String dateString;

    public CheckCommand(String dateString) {
        this.dateString = dateString;
    }

    /**
     * Executes the command by adding the task to the task list, saving the updated
     * list to storage, and displaying the success message to the user.
     *
     * @param tasks   The current list of tasks.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage handler for saving tasks to the disk.
     */
    @Override
    public void execute(TaskList tasks, UI ui, Storage storage) {
        ui.showActivitiesOn(dateString, tasks.getAllTasks(), tasks.getSize());
    }

}
