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

    @Override
    public void execute(TaskList tasks, UI ui, Storage storage) {
        ui.showActivitiesOn(dateString, tasks.getAllTasks(), tasks.getSize());
    }

}
