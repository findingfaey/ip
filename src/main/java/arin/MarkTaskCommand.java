package arin;

/**
 * Command to mark a task as done.
 */
public class MarkTaskCommand implements Command {

    private int taskIndex;

    /**
     * Creates a command to mark a task at the specified index as done.
     *
     * @param taskIndex The index of the task to mark as done.
     */
    public MarkTaskCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws ArinException {
        taskList.markTaskAsDone(taskIndex);
        ui.showTaskMarkedAsDone(taskList.getTask(taskIndex));
        storage.saveTasks(taskList.getTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
