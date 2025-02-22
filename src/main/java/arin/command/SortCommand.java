package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.Task;
import arin.task.TaskList;
import arin.ui.Ui;

import java.util.List;

/**
 * Represents a command to sort tasks by different criteria.
 */
public class SortCommand implements Command {

    /** Sorts by task due date (chronologically). */
    public static final String SORT_BY_DATE = "date";

    /** Sorts alphabetically by task description. */
    public static final String SORT_BY_NAME = "name";

    /** Sorts by task type (ToDo → Deadline → Event). */
    public static final String SORT_BY_TYPE = "type";

    /** Sorts by completion status (incomplete first, then completed). */
    public static final String SORT_BY_STATUS = "status";

    private final String sortCriterion;

    /**
     * Creates a SortCommand with the specified sorting criterion.
     *
     * @param sortCriterion The criterion to sort by ("date", "name", "type", or "status").
     */
    public SortCommand(final String sortCriterion) {
        this.sortCriterion = sortCriterion.toLowerCase();
    }

    /**
     * Executes the sort command, displaying tasks sorted by the specified criterion.
     *
     * @param taskList The task list to sort.
     * @param ui       The UI to display the sorted tasks.
     * @param storage  The storage (not used in this command).
     * @throws ArinException If an error occurs during execution.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws ArinException {
        List<Task> sortedTasks;

        switch (sortCriterion) {
        case SORT_BY_DATE:
            sortedTasks = taskList.getSortedByDeadline();
            break;
        case SORT_BY_NAME:
            sortedTasks = taskList.getSortedByDescription();
            break;
        case SORT_BY_TYPE:
            sortedTasks = taskList.getSortedByType();
            break;
        case SORT_BY_STATUS:
            sortedTasks = taskList.getSortedByStatus();
            break;
        default:
            throw new ArinException("Unknown sort criterion: " + sortCriterion +
                    ". Please use 'sort by date', 'sort by name', 'sort by type', or 'sort by status'.");
        }

        if (sortedTasks.isEmpty()) {
            ui.showError("No tasks to sort!");
        } else {
            ui.showSortedTaskList(sortedTasks, sortCriterion);
        }
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