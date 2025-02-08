package arin.task;

/**
 * Represents a simple to-do task.
 */
public class ToDo extends Task {

    /**
     * Creates a ToDo task with a description.
     *
     * @param description The task description.
     */
    public ToDo(String description) {
        super(description, TaskType.TODO);
    }

    /**
     * Returns the string format for saving the ToDo task.
     *
     * @return The formatted string for saving the task.
     */
    @Override
    public String toSaveString() {
        return "T | " + (isDone() ? "1" : "0") + " | " + description;
    }
}
