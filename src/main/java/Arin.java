import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

/**
 * The main class of the Arin chatbot program.
 */
public class Arin {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static final String BORDER = "____________________________________________________________";
    private static final String FILE_PATH = "./data/arin.txt"; // Define the file to save and load tasks

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

        // Load tasks from file
        loadTasks();

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

        // Save tasks to file before closing
        saveTasks();

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
                saveTasks();  // Save tasks after marking as done
            } else {
                throw new ArinException("Task number out of range!");
            }
        } catch (NumberFormatException e) {
            throw new ArinException("Please enter a valid task number to mark!");
        }
    }

    // Helper method to add tasks and prevent duplicates
    private static void addTask(Task newTask) throws ArinException {
        for (Task task : tasks) {
            // Check if a task with the same description already exists in the list
            if (task.getDescription().equals(newTask.getDescription()) &&
                    task.getTaskType() == newTask.getTaskType()) {
                throw new ArinException("This task already exists in the list.");
            }
        }
        tasks.add(newTask);
        printTaskAdded();
        saveTasks();  // Save tasks after adding the new task
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
        addTask(new ToDo(taskDescription));  // Add using the helper method
    }

    /**
     * Adds a Deadline task.
     *
     * @param input User input for adding a Deadline task.
     * @throws ArinException If the input is invalid.
     */
    private static void addDeadline(String input) throws ArinException {
        try {
            // Split by /by and ensure there are two parts
            String[] parts = input.substring(9).split(" /by ");
            if (parts.length < 2) {
                throw new ArinException("Invalid format! Use: deadline <task> /by <due date>");
            }

            String description = parts[0].trim();
            String dueDate = parts[1].trim();  // Extract the date part

            // Create a new Deadline task using the proper date format
            addTask(new Deadline(description, dueDate));  // Add using the helper method
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

            String description = parts[0];
            String fromDate = parts[1];
            String toDate = parts[2];  // Extract both dates

            addTask(new Event(description, fromDate, toDate));  // Add using the helper method
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
                saveTasks();  // Save tasks after deleting a task
            } else {
                throw new ArinException("Task number out of range! Please enter a valid task number.");
            }
        } catch (NumberFormatException e) {
            throw new ArinException("Invalid input! Please enter a valid task number to delete.");
        }
    }

    /**
     * Loads the tasks from the file.
     */
    private static void loadTasks() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return;  // If the file doesn't exist, no tasks are loaded
            }

            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String taskData = fileScanner.nextLine();
                String[] taskParts = taskData.split(" \\| ");
                String taskType = taskParts[0];
                boolean isDone = taskParts[1].equals("1");
                String taskDescription = taskParts[2];
                Task task;

                switch (taskType) {
                    case "T":
                        task = new ToDo(taskDescription);
                        if (isDone) {
                            task.setDone(true);
                        }
                        break;
                    case "D":
                        task = new Deadline(taskDescription, taskParts[3]);
                        if (isDone) {
                            task.setDone(true);
                        }
                        break;
                    case "E":
                        task = new Event(taskDescription, taskParts[3], taskParts[4]);
                        if (isDone) {
                            task.setDone(true);
                        }
                        break;
                    default:
                        continue;
                }
                tasks.add(task);
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found. Starting with an empty task list.");
        }
    }

    /**
     * Saves the tasks to the file.
     */
    private static void saveTasks() {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();  // Create parent directories if they do not exist
            file.createNewFile();  // Create the file if it doesn't exist
            PrintWriter writer = new PrintWriter(file);

            for (Task task : tasks) {
                String taskType = task.getTaskType().toString().substring(0, 1);  // Get the task type (T, D, E)
                String taskStatus = task.isDone() ? "1" : "0";
                String taskDescription = task.getDescription();
                String taskDetails = taskType + " | " + taskStatus + " | " + taskDescription;

                if (task instanceof Deadline) {
                    taskDetails += " | " + ((Deadline) task).getBy().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    taskDetails += " | " + event.getFrom().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                            + " | " + event.getTo().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                }
                writer.println(taskDetails);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks to file.");
        }
    }
}