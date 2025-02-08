package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.TaskList;
import arin.ui.Ui;

/**
 * Represents a command to exit the application.
 */
public class ExitCommand implements Command {

    /**
     * Executes the command to display an exit message to the user.
     *
     * @param taskList The task list (not used in this command).
     * @param ui       The UI to display messages to the user.
     * @param storage  The storage (not used in this command).
     * @throws ArinException Never thrown in this command.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws ArinException {
        ui.showExit();
    }

    /**
     * Indicates that this command should cause the application to exit.
     *
     * @return true as this command exits the application.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
