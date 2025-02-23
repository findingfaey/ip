package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.TaskList;
import arin.ui.Ui;

/**
 * Represents a command to unmark a task (mark as not done).
 */
public class UnmarkTaskCommand implements Command {

    private final int taskIndex;

    /**
     * Creates a command to unmark the task at the specified index.
     *
     * @param taskIndex The index of the task to unmark.
     */
    public UnmarkTaskCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Executes the command to unmark the specified task,
     * display the change, and save the updated task list.
     *
     * @param taskList The task list containing the task.
     * @param ui       The UI to display messages to the user.
     * @param storage  The storage to save the updated task list.
     * @throws ArinException If there is an error unmarking the task.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws ArinException {
        // Convert from 1-based user index to 0-based internal index
        int internalIndex = taskIndex - 1;  // Bug fix: Subtract 1 to convert from user index to internal index

        if (internalIndex < 0 || internalIndex >= taskList.getTasks().size()) {
            throw new ArinException("Invalid task index. Please provide a number between 1 and "
                    + taskList.getTasks().size() + ".");
        }

        taskList.markTaskAsNotDone(internalIndex);
        ui.showTaskMarkedAsNotDone(taskList.getTask(internalIndex));
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
