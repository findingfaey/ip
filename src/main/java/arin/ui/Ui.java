package arin.ui;

import arin.ArinException;
import arin.command.Command;
import arin.storage.Storage;
import arin.task.Task;
import arin.task.TaskList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles user interactions and displays messages.
 */
public class Ui {
    private final Scanner scanner;
    private final TaskList taskList;
    private final Storage storage;
    private boolean isGuiMode = false;
    private String latestResponse = "";

    /**
     * Creates an Ui object for handling user input and output.
     *
     * @param taskList The task list to operate on.
     * @param storage  The storage system.
     */
    public Ui(TaskList taskList, Storage storage) {
        this.scanner = new Scanner(System.in);
        this.taskList = taskList;
        this.storage = storage;
    }

    /**
     * Sets the UI to GUI mode.
     */
    public void setGuiMode() {
        this.isGuiMode = true;
    }

    /**
     * Processes user input and returns the chatbot's response.
     *
     * @param input The user input string.
     * @return The chatbot's response.
     */
    public String processInput(String input) {
        try {
            Command command = Parser.parse(input);
            command.execute(taskList, this, storage);
            return latestResponse;
        } catch (ArinException e) {
            showError(e.getMessage());
            return latestResponse;
        }
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        String message = "Hello! I'm Arin.\nWhat can I do for you?";
        if (!isGuiMode) {
            System.out.println(message);
        }
        latestResponse = message;
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
        if (!isGuiMode) {
            System.out.println("____________________________________________________________");
        }
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        String formattedMessage = "Error: " + message;
        if (!isGuiMode) {
            System.out.println(formattedMessage);
        }
        latestResponse = formattedMessage;
    }

    /**
     * Displays a message indicating a task has been added.
     *
     * @param task The task that was added.
     */
    public void showTaskAdded(Task task) {
        String message = "Got it. I've added this task:\n   " + task;
        if (!isGuiMode) {
            System.out.println(message);
        }
        latestResponse = message;
    }

    /**
     * Displays a message indicating a task has been deleted.
     */
    public void showTaskDeleted() {
        String message = "Task has been deleted.";
        if (!isGuiMode) {
            System.out.println(message);
        }
        latestResponse = message;
    }

    /**
     * Displays the exit message when the program is closing.
     */
    public void showExit() {
        String message = "Goodbye! Hope to see you again soon.";
        if (!isGuiMode) {
            System.out.println(message);
        }
        latestResponse = message;
    }

    /**
     * Displays a message indicating a task has been marked as done.
     *
     * @param task The task that was marked as done.
     */
    public void showTaskMarkedAsDone(Task task) {
        String message = "Nice! I've marked this task as done:\n   " + task;
        if (!isGuiMode) {
            System.out.println(message);
        }
        latestResponse = message;
    }

    /**
     * Displays matching tasks found from a search query.
     *
     * @param matchingTasks The list of tasks matching the search query.
     */
    public void showMatchingTasks(List<Task> matchingTasks) {
        StringBuilder messageBuilder = new StringBuilder();
        if (matchingTasks.isEmpty()) {
            messageBuilder.append("No matching tasks found.");
        } else {
            messageBuilder.append("Here are the matching tasks in your list:\n");
            int index = 1;
            for (Task task : matchingTasks) {
                messageBuilder.append(index).append(". ").append(task).append("\n");
                index++;
            }
        }

        String message = messageBuilder.toString().trim();
        if (!isGuiMode) {
            System.out.println(message);
        }
        latestResponse = message;
    }

    /**
     * Displays a message indicating a task has been marked as not done.
     *
     * @param task The task that was marked as not done.
     */
    public void showTaskMarkedAsNotDone(Task task) {
        String message = "OK, I've marked this task as not done yet:\n   " + task;

        if (!isGuiMode) {
            System.out.println(message);
        }

        latestResponse = message;
    }

    /**
     * Gets the latest response generated by the UI.
     *
     * @return The latest response string.
     */
    public String getLatestResponse() {
        return latestResponse;
    }

    /**
     * Displays the list of all tasks.
     *
     * @param tasks The list of tasks to show.
     */
    public void showTaskList(List<Task> tasks) {
        StringBuilder messageBuilder = new StringBuilder();

        if (tasks.isEmpty()) {
            messageBuilder.append("No tasks to display!");
        } else {
            messageBuilder.append("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                messageBuilder.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
            }
        }

        String message = messageBuilder.toString().trim();
        if (!isGuiMode) {
            System.out.println(message);
        }

        latestResponse = message;
    }

    /**
     * Displays the list of sorted tasks.
     *
     * @param tasks The sorted list of tasks to show.
     * @param sortCriterion The criterion used for sorting.
     */
    public void showSortedTaskList(List<Task> tasks, String sortCriterion) {
        StringBuilder messageBuilder = new StringBuilder();

        if (tasks.isEmpty()) {
            messageBuilder.append("No tasks to display!");
        } else {
            String sortByText;
            switch (sortCriterion) {
            case "date":
                sortByText = "date (chronologically)";
                break;
            case "name":
                sortByText = "description (alphabetically)";
                break;
            case "type":
                sortByText = "task type";
                break;
            case "status":
                sortByText = "completion status";
                break;
            default:
                sortByText = sortCriterion;
            }

            messageBuilder.append("Here are your tasks sorted by ").append(sortByText).append(":\n");
            for (int i = 0; i < tasks.size(); i++) {
                messageBuilder.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
            }
        }

        String message = messageBuilder.toString().trim();
        if (!isGuiMode) {
            System.out.println(message);
        }

        latestResponse = message;
    }

    /**
     * Displays the help message showing available commands.
     *
     * @param helpMessage The help message to display.
     */
    public void showHelp(String helpMessage) {
        String message = "📖 Command Guide\n\n" + helpMessage;
        if (!isGuiMode) {
            System.out.println(message);
        }
        latestResponse = message;
    }

}