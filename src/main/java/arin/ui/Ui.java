package arin.ui;

import arin.task.Task;

import java.util.List;
import java.util.Scanner;

/**
 * Handles user interactions and displays messages.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Creates a Ui object for handling user input and output.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm Arin.");
        System.out.println("What can I do for you?");
    }

    /**
     * Reads a command input from the user.
     *
     * @return The user input as a string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a horizontal line as a separator.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Displays a message indicating a task has been added.
     *
     * @param task The task that was added.
     */
    public void showTaskAdded(Task task) {
        System.out.println("Got it. I've added this task:");
        System.out.println("   " + task);
    }

    /**
     * Displays a message indicating a task has been deleted.
     */
    public void showTaskDeleted() {
        System.out.println("Task has been deleted.");
    }

    /**
     * Displays the exit message when the program is closing.
     */
    public void showExit() {
        System.out.println("Goodbye! Hope to see you again soon.");
    }

    /**
     * Displays a message indicating a task has been marked as done.
     *
     * @param task The task that was marked as done.
     */
    public void showTaskMarkedAsDone(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("   " + task);
    }

    /**
     * Displays a task to the user.
     *
     * @param task The task to be displayed.
     */
    public void showTask(Task task) {
        System.out.println(task);
    }

    /**
     * Displays matching tasks found from a search query.
     *
     * @param matchingTasks The list of tasks matching the search query.
     */
    public void showMatchingTasks(List<Task> matchingTasks) {
        if (matchingTasks.isEmpty()) {
            System.out.println("No matching tasks found.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            int index = 1;
            for (Task task : matchingTasks) {
                System.out.println(index + ". " + task);
                index++;
            }
        }
    }
}
