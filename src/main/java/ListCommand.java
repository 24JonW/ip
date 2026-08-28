import java.io.IOException;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, UI ui, Storage storage) throws JonathanException, IOException {
        ui.showTaskList(tasks.getAllTasks(), tasks.getSize());

    }
}
