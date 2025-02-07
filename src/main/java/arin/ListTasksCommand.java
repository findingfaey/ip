package arin;

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
