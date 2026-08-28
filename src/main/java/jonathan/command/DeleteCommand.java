import java.io.IOException;

public class DeleteCommand extends Command {
    private int taskIndex;
    public DeleteCommand(int taskIndex) {
        this.taskIndex= taskIndex;
    }
    @Override
    public void execute(TaskList tasks, UI ui, Storage storage) throws JonathanException, IOException {
        Parser.require(tasks.getSize() > 0, "There are no tasks to delete.");
        Parser.require(taskIndex >= 0 && taskIndex < tasks.getSize(),
                "Choose a task number from 1 to " + tasks.getSize() + ".");
        Task deletedtask= tasks.deleteTask(taskIndex);
        storage.save(tasks.getAllTasks(), tasks.getSize());
        ui.showDeleted(deletedtask, tasks.getSize());

    }
}
