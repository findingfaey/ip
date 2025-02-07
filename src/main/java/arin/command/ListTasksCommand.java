package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.TaskList;
import arin.ui.Ui;

/**
 * Command to list all tasks.
 */
public class ListTasksCommand implements Command {

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws ArinException {
        taskList.listTasks(ui);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
