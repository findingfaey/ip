import java.util.ArrayList;
import java.util.Scanner;

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

/**
 * Represents a ToDo task, a subclass of Task.
 */
class ToDo extends Task {
    /**
     * Constructs a ToDo task with the given description.
     *
     * @param description The description of the task.
     */
    public ToDo(String description) {
        super(description, TaskType.TODO);  // Set the task type
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}


/**
 * Represents a Deadline task, a subclass of Task.
 */
class Deadline extends Task {
    protected String by;

    /**
     * Constructs a Deadline task with the given description and due date.
     *
     * @param description The description of the task.
     * @param by The due date of the task.
     */
    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);  // Set the task type
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}


/**
 * Represents an Event task, a subclass of Task.
 */
class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructs an Event task with the given description, start time, and end time.
     *
     * @param description The description of the task.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public Event(String description, String from, String to) {
        super(description, TaskType.EVENT);  // Set the task type
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}


/**
 * Represents a custom exception for the Arin chatbot.
 */
class ArinException extends Exception {
    /**
     * Constructs an ArinException with the given message.
     *
     * @param message The error message.
     */
    public ArinException(String message) {
        super(message);
    }
}

/**
 * The main class of the Arin chatbot program.
 */
public class Arin {
    private static ArrayList<Task> tasks = new ArrayList<>(); // Use ArrayList for dynamic sizing

    /**
     * Starts the Arin chatbot.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Arin.");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        while (scanner.hasNextLine()) {  // Use hasNextLine() to check for end of input
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                break;  // Exit the loop if the input is empty
            }

            try {
                if (input.equals("bye")) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Bye. Hope to see you again soon!");
                    System.out.println("____________________________________________________________");
                    break;
                } else if (input.equals("list")) {
                    listTasks();
                } else if (input.startsWith("mark ")) {
                    markTaskAsDone(input);
                } else if (input.startsWith("delete ")) {  // Handle delete command
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
                System.out.println("____________________________________________________________");
                System.out.println(" " + e.getMessage());
                System.out.println("____________________________________________________________");
            }
        }

        scanner.close();
    }

    /**
     * Lists all tasks in the task list.
     */
    private static void listTasks() {
        System.out.println("____________________________________________________________");
        if (tasks.isEmpty()) {
            System.out.println(" No tasks added yet!");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + ". " + tasks.get(i));
            }
        }
        System.out.println("____________________________________________________________");
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
                System.out.println("____________________________________________________________");
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + tasks.get(taskIndex));
                System.out.println("____________________________________________________________");
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
        tasks.add(new ToDo(taskDescription));  // Now using ToDo class
        printTaskAdded();  // Show only the last added task and the current count
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
        System.out.println("____________________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + tasks.get(tasks.size() - 1));
        System.out.println(" Now you have " + tasks.size() + (tasks.size() == 1 ? " task" : " tasks") + " in the list.");
        System.out.println("____________________________________________________________");
    }

    /**
     * Deletes a task from the list.
     *
     * @param input User input containing the task number to delete.
     * @throws ArinException If the input is invalid.
     */
    private static void deleteTask(String input) throws ArinException {
        try {
            int taskIndex = Integer.parseInt(input.substring(7)) - 1;  // Extract task index
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                Task removedTask = tasks.remove(taskIndex);  // Remove task from list
                System.out.println("____________________________________________________________");
                System.out.println(" Noted. I've removed this task:");
                System.out.println("   " + removedTask);
                System.out.println(" Now you have " + tasks.size() + (tasks.size() == 1 ? " task" : " tasks") + " in the list.");
                System.out.println("____________________________________________________________");
            } else {
                throw new ArinException("Task number out of range! Please enter a valid task number.");
            }
        } catch (NumberFormatException e) {
            throw new ArinException("Invalid input! Please enter a valid task number to delete.");
        }
    }
}