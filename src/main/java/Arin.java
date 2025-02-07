import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main class of the Arin chatbot program.
 */
public class Arin {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static final String BORDER = "____________________________________________________________";

    // Utility method to print formatted output
    private static void printOutput(String... messages) {
        for (String message : messages) {
            System.out.println(message);
        }
        System.out.println(BORDER);
    }

    /**
     * Starts the Arin chatbot.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(BORDER);
        System.out.println(" Hello! I'm Arin.");
        System.out.println(" What can I do for you?");
        System.out.println(BORDER);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                break;
            }

            try {
                if (input.equals("bye")) {
                    printOutput("Bye. Hope to see you again soon!");
                    break;
                } else if (input.equals("list")) {
                    listTasks();
                } else if (input.startsWith("mark ")) {
                    markTaskAsDone(input);
                } else if (input.startsWith("delete ")) {
                    deleteTask(input);
                } else if (input.startsWith("todo ")) {
                    addToDo(input);
                } else if (input.startsWith("deadline ")) {
                    addDeadline(input);
                } else if (input.startsWith("event ")) {
                    addEvent(input);
                } else {
                    throw new ArinException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (ArinException e) {
                printOutput(e.getMessage());
            }
        }

        scanner.close();
    }

    /**
     * Lists all tasks in the task list.
     */
    private static void listTasks() {
        if (tasks.isEmpty()) {
            printOutput("No tasks added yet!");
        } else {
            StringBuilder output = new StringBuilder("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                output.append("\n").append(i + 1).append(". ").append(tasks.get(i));
            }
            printOutput(output.toString());
        }
    }

    /**
     * Marks a task as done.
     *
     * @param input User input containing the task number to mark as done.
     * @throws ArinException If the input is invalid.
     */
    private static void markTaskAsDone(String input) throws ArinException {
        try {
            int taskIndex = Integer.parseInt(input.substring(5)) - 1;
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                tasks.get(taskIndex).markAsDone();
                printOutput(
                        "Nice! I've marked this task as done:",
                        "   " + tasks.get(taskIndex)
                );
            } else {
                throw new ArinException("Task number out of range!");
            }
        } catch (NumberFormatException e) {
            throw new ArinException("Please enter a valid task number to mark!");
        }
    }

    /**
     * Adds a ToDo task.
     *
     * @param input User input for adding a ToDo task.
     * @throws ArinException If the input is invalid.
     */
    private static void addToDo(String input) throws ArinException {
        String taskDescription = input.replaceFirst("todo", "").trim();
        if (taskDescription.isEmpty()) {
            throw new ArinException("The description of a todo cannot be empty.");
        }
        tasks.add(new ToDo(taskDescription));
        printTaskAdded();
    }

    /**
     * Adds a Deadline task.
     *
     * @param input User input for adding a Deadline task.
     * @throws ArinException If the input is invalid.
     */
    private static void addDeadline(String input) throws ArinException {
        try {
            String[] parts = input.substring(9).split(" /by ");
            if (parts.length < 2) {
                throw new ArinException("Invalid format! Use: deadline <task> /by <due date>");
            }
            tasks.add(new Deadline(parts[0], parts[1]));
            printTaskAdded();
        } catch (Exception e) {
            throw new ArinException("Invalid format! Use: deadline <task> /by <due date>");
        }
    }

    /**
     * Adds an Event task.
     *
     * @param input User input for adding an Event task.
     * @throws ArinException If the input is invalid.
     */
    private static void addEvent(String input) throws ArinException {
        try {
            String[] parts = input.substring(6).split(" /from | /to ");
            if (parts.length < 3) {
                throw new ArinException("Invalid format! Use: event <task> /from <start> /to <end>");
            }
            tasks.add(new Event(parts[0], parts[1], parts[2]));
            printTaskAdded();
        } catch (Exception e) {
            throw new ArinException("Invalid format! Use: event <task> /from <start> /to <end>");
        }
    }

    /**
     * Prints the last added task.
     */
    private static void printTaskAdded() {
        printOutput(
                "Got it. I've added this task:",
                "   " + tasks.get(tasks.size() - 1),
                "Now you have " + tasks.size() + (tasks.size() == 1 ? " task" : " tasks") + " in the list."
        );
    }

    /**
     * Deletes a task from the list.
     *
     * @param input User input containing the task number to delete.
     * @throws ArinException If the input is invalid.
     */
    private static void deleteTask(String input) throws ArinException {
        try {
            int taskIndex = Integer.parseInt(input.substring(7)) - 1;
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                Task removedTask = tasks.remove(taskIndex);
                printOutput(
                        "Noted. I've removed this task:",
                        "   " + removedTask,
                        "Now you have " + tasks.size() + (tasks.size() == 1 ? " task" : " tasks") + " in the list."
                );
            } else {
                throw new ArinException("Task number out of range! Please enter a valid task number.");
            }
        } catch (NumberFormatException e) {
            throw new ArinException("Invalid input! Please enter a valid task number to delete.");
        }
    }
}