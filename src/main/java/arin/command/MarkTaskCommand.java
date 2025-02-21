package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.TaskList;
import arin.ui.Ui;

/**
 * Represents a command to mark a task as done.
 */
public class MarkTaskCommand implements Command {

    private final int taskIndex;

    /**
     * Creates a command to mark the task at the specified index as done.
     *
     * @param taskIndex The index of the task to mark as done.
     */
    public MarkTaskCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Executes the command to mark the specified task as done,
     * display the change, and save the updated task list.
     *
     * @param taskList The task list containing the task.
     * @param ui       The UI to display messages to the user.
     * @param storage  The storage to save the updated task list.
     * @throws ArinException If there is an error marking the task as done.
     */
    // In MarkTaskCommand.java
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws ArinException {
        // Convert from 1-based user index to 0-based internal index
        int internalIndex = taskIndex - 1;

        // Check if the index is valid
        if (internalIndex < 0 || internalIndex >= taskList.getTasks().size()) {
            throw new ArinException("Invalid task index. Please provide a number between 1 and "
                    + taskList.getTasks().size() + ".");
        }

        taskList.markTaskAsDone(internalIndex);
        ui.showTaskMarkedAsDone(taskList.getTask(internalIndex));
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
