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
