package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.Task;
import arin.task.TaskList;
import arin.ui.Ui;

/**
 * Command to add a task (ToDo, Deadline, Event).
 */
public class AddTaskCommand implements Command {

    private Task task;

    /**
     * Creates a command to add the specified task.
     *
     * @param task The task to be added.
     */
    public AddTaskCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws ArinException {
        taskList.addTask(task);
        ui.showTaskAdded(task);
        storage.saveTasks(taskList.getTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
