package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.Task;
import arin.task.TaskList;
import arin.ui.Ui;
import java.util.List;

/**
 * Represents a command to find tasks that match a keyword.
 */
public class FindCommand implements Command {

    private final String keyword;

    /**
     * Creates a FindCommand with a keyword to search for.
     *
     * @param keyword The keyword to search in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command to search for tasks containing the keyword.
     *
     * @param taskList The task list to search.
     * @param ui       The UI to display matching tasks.
     * @param storage  The storage (not used in this command).
     * @throws ArinException If an error occurs during execution.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws ArinException {
        List<Task> matchingTasks = taskList.findTasks(keyword);
        ui.showMatchingTasks(matchingTasks);
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
