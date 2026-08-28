package jonathan.command;

import java.io.IOException;
import jonathan.task.Task;
import jonathan.storage.Storage;
import jonathan.task.TaskList;
import jonathan.ui.UI;
import jonathan.parser.Parser;
import jonathan.JonathanException;



public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, UI ui, Storage storage) throws JonathanException, IOException {
        ui.showTaskList(tasks.getAllTasks(), tasks.getSize());

    }
}
