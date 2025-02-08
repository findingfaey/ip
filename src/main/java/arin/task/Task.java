package arin.task;

/**
 * Represents an abstract task with a description and completion status.
 */
public abstract class Task {

    protected final String description;
    protected boolean isDone;
    protected final TaskType taskType;

    /**
     * Creates a task with the given description and type.
     *
     * @param description The task description.
     * @param taskType The type of the task (ToDo, Deadline, Event).
     */
    public Task(String description, TaskType taskType) {
        this.description = description;
        this.isDone = false;
        this.taskType = taskType;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public boolean isDone() {
        return isDone;
    }

    public String toString() {
        return (isDone ? "[X]" : "[ ]") + " " + description;
    }

    public abstract String toSaveString();
}
