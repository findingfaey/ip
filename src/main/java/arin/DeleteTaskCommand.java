package arin;

/**
 * Command to delete a task.
 */
public class DeleteTaskCommand implements Command {

    private int taskIndex;

    /**
     * Creates a command to delete the task at the specified index.
     *
     * @param taskIndex The index of the task to delete.
     */
    public DeleteTaskCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws ArinException {
        taskList.deleteTask(taskIndex);
        ui.showTaskDeleted();
        storage.saveTasks(taskList.getTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
