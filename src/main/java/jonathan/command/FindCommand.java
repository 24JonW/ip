package jonathan.command;
import java.io.IOException;
import jonathan.task.Task;
import jonathan.storage.Storage;
import jonathan.task.TaskList;
import jonathan.ui.UI;
import jonathan.parser.Parser;
import jonathan.JonathanException;

public class FindCommand extends Command{
    private String keyword;

    public FindCommand(String keyword) {
        this.keyword= keyword;
    }

    /**
     * Executes the search and delegates the display of matching tasks to the UI.
     *
     * @param tasks   The current list of tasks.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage handler (unused for this command).
     */
    @Override
    public void execute(TaskList tasks, UI ui, Storage storage) throws JonathanException {
        ui.showFoundTasks(keyword, tasks.getAllTasks(), tasks.getSize());
    }
}
