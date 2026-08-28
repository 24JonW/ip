import jonathan.parser.Parser;
import jonathan.storage.Storage;
import jonathan.task.Task;
import jonathan.task.TaskList;

import java.io.IOException;

public class MarkCommand extends Command {
    private int taskIndex;
    private boolean isMarkingAsDone;

    public MarkCommand(int taskIndex, boolean isMarkingAsDone) {
        this.taskIndex = taskIndex;
        this.isMarkingAsDone = isMarkingAsDone;
    }
    @Override
    public void execute(TaskList tasks, UI ui, Storage storage) throws JonathanException, IOException {
        Parser.require(tasks.getSize() > 0, "There are no tasks to modify.");
        Parser.require(taskIndex >= 0 && taskIndex < tasks.getSize(),
                "Choose a task number from 1 to " + tasks.getSize() + ".");

        Task task = tasks.getTask(taskIndex);
        if (isMarkingAsDone) {
            task.markAsDone();
            ui.showMarked(task);
        } else {
            task.markAsNotDone();
            ui.showUnmarked(task);
        }
        storage.save(tasks.getAllTasks(), tasks.getSize());
    }
}
