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
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid task format in save file!");
        }

        String type = parts[0];
        Task task = null;

        try {
            switch (type) {
            case "T":
                task = new ToDo(parts[2]);
                break;
            case "D":
                if (parts.length < 4) {
                    throw new IllegalArgumentException("Deadline task is missing the due date!");
                }
                task = new Deadline(parts[2], parts[3]);
                break;
            case "E":
                if (parts.length < 5) {
                    throw new IllegalArgumentException("Event task is missing start or end time!");
                }
                task = new Event(parts[2], parts[3], parts[4]);
                break;
            default:
                throw new IllegalArgumentException("Unknown task type: " + type);
            }

            if (parts[1].equals("1")) {
                task.markAsDone();
            }

            return task;
        } catch (Exception e) {
            System.err.println("Error parsing task: " + taskData);
            System.err.println("Error details: " + e.getMessage());
            throw new IllegalArgumentException("Failed to parse task: " + e.getMessage());
        }
    }
    /**
     * Returns the task's description.
     *
     * @return The task description as a string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }
}
