package arin.task;

import java.util.Collection;

/**
 * Represents an abstract task with a description and completion status.
 */
public abstract class Task {

    protected String description;
    protected boolean isDone;
    protected TaskType taskType;

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

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Checks if the task is completed.
     *
     * @return True if the task is completed, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns a formatted string representation of the task.
     *
     * @return The formatted string representation.
     */
    public String toString() {
        return (isDone ? "[X]" : "[ ]") + " " + description;
    }

    /**
     * Converts the task into a string format for saving.
     *
     * @return The formatted string for saving the task.
     */
    public abstract String toSaveString();

    /**
     * Parses a saved task string and returns the corresponding Task object.
     *
     * @param taskData The saved task string.
     * @return The corresponding Task object.
     */
    public static Task parseTask(String taskData) {
        String[] parts = taskData.split(" \\| ");
        String type = parts[0];
        Task task = null;
        switch (type) {
        case "T":
            task = new ToDo(parts[2]);
            break;
        case "D":
            task = new Deadline(parts[2], parts[3]);
            break;
        case "E":
            task = new Event(parts[2], parts[3], parts[4]);
            break;
        }
        if (parts[1].equals("1")) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Returns the task's description.
     *
     * @return The task description as a string.
     */
    public String getDescription() {
        return description;
    }
}
