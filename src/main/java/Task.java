/**
 * Represents a task with a description and completion status.
 */
abstract class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType taskType;  // Add taskType here

    /**
     * Constructs a Task with the given description and task type.
     *
     * @param description The task description.
     * @param taskType The type of the task (ToDo, Deadline, Event).
     */
    public Task(String description, TaskType taskType) {
        this.description = description;
        this.isDone = false;
        this.taskType = taskType;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Returns the status icon for the task.
     *
     * @return "[X]" if done, "[ ]" otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    public String toString() {
        return getStatusIcon() + " " + description;
    }

    public TaskType getTaskType() {
        return taskType;
    }
}
