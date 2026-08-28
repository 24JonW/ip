import java.io.IOException;

public class AddCommand extends Command {
    private Task task;
    public AddCommand(Task task) {
        this.task= task;
    }
    @Override
    public void execute(TaskList tasks, UI ui, Storage storage) throws JonathanException, IOException {
        Parser.require(tasks.getSize() <100, "The task list is full");
        tasks.AddTask(task);
        storage.save(tasks.getAllTasks(), tasks.getSize());
        ui.showAdded(this.task, tasks.getSize());

    }

}
