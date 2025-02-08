package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.TaskList;
import arin.ui.Ui;

/**
 * Represents a generic command that can be executed.
 */
public interface Command {

    /**
     * Executes the command with the given task list, UI, and storage.
     *
     * @param taskList The task list to operate on.
     * @param ui       The UI to interact with the user.
     * @param storage  The storage to save any changes.
     * @throws ArinException If execution encounters an issue.
     */
    void execute(TaskList taskList, Ui ui, Storage storage) throws ArinException;

    /**
     * Determines whether this command will exit the application.
     *
     * @return true if the command should exit the application, false otherwise.
     */
    boolean isExit();
}
