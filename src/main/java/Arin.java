import java.util.Scanner;

class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }
}

class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}

class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}

public class Arin {
    private static Task[] tasks = new Task[100]; // Array of Tasks
    private static int taskCount = 0; // Keep track of task count

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Arin.");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        while (true) {
            String input = scanner.nextLine();

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
                } else if (input.startsWith("todo ")) {
                    addToDo(input);
                } else if (input.startsWith("deadline ")) {
                    addDeadline(input);
                } else if (input.startsWith("event ")) {
                    addEvent(input);
                } else {
                    throw new Exception("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (Exception e) {
                System.out.println("____________________________________________________________");
                System.out.println(" " + e.getMessage());
                System.out.println("____________________________________________________________");
            }
        }

        scanner.close();
    }

    // 📝 List all tasks
    private static void listTasks() {
        System.out.println("____________________________________________________________");
        if (taskCount == 0) {
            System.out.println(" No tasks added yet!");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < taskCount; i++) {
                System.out.println(" " + (i + 1) + ". " + tasks[i]);
            }
        }
        System.out.println("____________________________________________________________");
    }

    // ✅ Mark a task as done
    private static void markTaskAsDone(String input) throws Exception {
        try {
            int taskIndex = Integer.parseInt(input.substring(5)) - 1;
            if (taskIndex >= 0 && taskIndex < taskCount) {
                tasks[taskIndex].markAsDone();
                System.out.println("____________________________________________________________");
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + tasks[taskIndex]);
                System.out.println("____________________________________________________________");
            } else {
                throw new Exception("Task number out of range!");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Please enter a valid task number to mark!");
        }
    }

    // ➕ Add a ToDo task
    private static void addToDo(String input) throws Exception {
        String taskDescription = input.replaceFirst("todo", "").trim();
        if (taskDescription.isEmpty()) {
            throw new Exception("The description of a todo cannot be empty.");
        }
        tasks[taskCount++] = new ToDo(taskDescription);
        printTaskAdded();
    }

    // 📌 Add a Deadline task
    private static void addDeadline(String input) throws Exception {
        try {
            String[] parts = input.substring(9).split(" /by ");
            if (parts.length < 2) {
                throw new Exception("Invalid format! Use: deadline <task> /by <due date>");
            }
            tasks[taskCount++] = new Deadline(parts[0], parts[1]);
            printTaskAdded();
        } catch (Exception e) {
            throw new Exception("Invalid format! Use: deadline <task> /by <due date>");
        }
    }

    // 🎉 Add an Event task
    private static void addEvent(String input) throws Exception {
        try {
            String[] parts = input.substring(6).split(" /from | /to ");
            if (parts.length < 3) {
                throw new Exception("Invalid format! Use: event <task> /from <start> /to <end>");
            }
            tasks[taskCount++] = new Event(parts[0], parts[1], parts[2]);
            printTaskAdded();
        } catch (Exception e) {
            throw new Exception("Invalid format! Use: event <task> /from <start> /to <end>");
        }
    }

    // 📢 Print the last added task
    private static void printTaskAdded() {
        System.out.println("____________________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + tasks[taskCount - 1]);
        System.out.println(" Now you have " + taskCount + (taskCount == 1 ? " task" : " tasks") + " in the list.");
        System.out.println("____________________________________________________________");
    }
}
