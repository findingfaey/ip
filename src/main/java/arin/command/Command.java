package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.TaskList;
import arin.ui.Ui;

/**
 * Represents a command that can be executed.
 */
public interface Command {
    void execute(TaskList taskList, Ui ui, Storage storage) throws ArinException;
    boolean isExit();
}
