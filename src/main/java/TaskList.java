public class TaskList {
    private Task[] tasks;
    private int itemCount;
    private static final int MAX_TASKS= 100;

    public TaskList() {
        this.tasks= new Task[MAX_TASKS];
        this.itemCount = 0;
    }

    public void AddTask(Task task) {
        this.tasks[itemCount] = task;
        this.itemCount++;
    }

    public Task deleteTask(int index) {
        Task removedTask= tasks[index];
        for (int i= index; i<itemCount-1; i++) {
            tasks[i]= tasks[i+1];
        }
        tasks[itemCount-1]= null;
        itemCount--;
        return removedTask;
    }

    public Task getTask(int index) {
        return tasks[index];
    }

    public int getSize() {
        return this.itemCount;
    }
    public Task[] getAllTasks() {
        return this.tasks;
    }
}
