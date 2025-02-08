package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.TaskList;
import arin.ui.Ui;

/**
 * Represents a command to list all tasks in the task list.
 */
public class ListTasksCommand implements Command {

    /**
     * Executes the command to display all tasks in the task list.
     *
     * @param taskList The task list to be displayed.
     * @param ui       The UI to display messages to the user.
     * @param storage  The storage (not used in this command).
     * @throws ArinException If there is an error during execution.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws ArinException {
        taskList.listTasks(ui);
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
