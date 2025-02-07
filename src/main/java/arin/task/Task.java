package arin.task;

import arin.TaskType;

public abstract class Task {

    protected String description;
    protected boolean isDone;
    protected TaskType taskType;  // Task type (ToDo, Deadline, Event)

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

    public abstract String toSaveString();  // Method for storing tasks

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
}
