package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.Task;
import arin.task.TaskList;
import arin.ui.Ui;

/**
 * Represents a command to add a task (ToDo, Deadline, Event) to the task list.
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

    /**
     * Executes the command to add the task to the task list,
     * display the addition, and save the updated task list.
     *
     * @param taskList The task list to add the task to.
     * @param ui       The UI to display messages to the user.
     * @param storage  The storage to save the updated task list.
     * @throws ArinException If there is an error saving the task.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws ArinException {
        taskList.addTask(task);
        ui.showTaskAdded(task);
        storage.saveTasks(taskList.getTasks());
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
