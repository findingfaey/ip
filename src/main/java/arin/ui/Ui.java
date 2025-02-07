package arin.ui;

import arin.task.Task;

import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("Hello! I'm Arin.");
        System.out.println("What can I do for you?");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public void showTaskAdded(Task task) {
        System.out.println("Got it. I've added this task:");
        System.out.println("   " + task);
    }

    public void showTaskDeleted() {
        System.out.println("Task has been deleted.");
    }

    public void showExit() {
        System.out.println("Goodbye! Hope to see you again soon.");
    }

    public void showTaskMarkedAsDone(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("   " + task);
    }

    public void showTask(Task task) {
        System.out.println(task);
    }
}
