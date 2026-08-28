import java.io.IOException;

public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, UI ui, Storage storage) throws JonathanException, IOException {
        ui.showGoodbye();
    }
    @Override
    public boolean isExit() {
        return true;
    }


}
