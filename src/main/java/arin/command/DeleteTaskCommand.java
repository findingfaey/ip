package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.TaskList;
import arin.ui.Ui;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteTaskCommand implements Command {

    private final int taskIndex;

    /**
     * Creates a command to delete the task at the specified index.
     *
     * @param taskIndex The index of the task to delete.
     */
    public DeleteTaskCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Executes the command to delete the task from the task list,
     * display the deletion, and save the updated task list.
     *
     * @param taskList The task list to remove the task from.
     * @param ui       The UI to display messages to the user.
     * @param storage  The storage to save the updated task list.
     * @throws ArinException If there is an error deleting the task.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws ArinException {
        taskList.deleteTask(taskIndex);
        ui.showTaskDeleted();
        storage.saveTasks(taskList.getTasks());
    }

    /**
     * Indicates whether this command should cause the application to exit.
     *
     * @return false as this command does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
