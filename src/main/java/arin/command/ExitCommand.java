package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.TaskList;
import arin.ui.Ui;

/**
 * Command to exit the program.
 */
public class ExitCommand implements Command {

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws ArinException {
        ui.showExit();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
