package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.Task;
import arin.task.TaskList;
import arin.ui.Ui;

/**
 * Represents a command that adds a task (ToDo, Deadline, Event).
 */
public class AddTaskCommand implements Command {

    private final Task task;

    /**
     * Creates a command to add the specified task.
     *
     * @param task The task to be added.
     */
    public AddTaskCommand(final Task task) {
        this.task = task;
    }

    @Override
    public void execute(final TaskList taskList, final Ui ui, final Storage storage) throws ArinException {
        taskList.addTask(task);
        ui.showTaskAdded(task);
        storage.saveTasks(taskList.getTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
