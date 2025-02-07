package arin;

import arin.task.Task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(int index) {
        tasks.remove(index);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public void markTaskAsDone(int index) {
        tasks.get(index).markAsDone();
    }

    public void listTasks(Ui ui) {
        if (tasks.isEmpty()) {
            ui.showError("No tasks to display!");
        } else {
            for (Task task : tasks) {
                ui.showTask(task);
            }
        }
    }
}