package arin;

import arin.task.Task;

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
