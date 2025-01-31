import java.util.Scanner;

public class Arin {
    public static void main(String[] args) {
        // ASCII Logo
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        // Greeting message
        System.out.println("Hello from\n" + logo);
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Arin.");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        // Scanner to read user input
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            // Exit condition
            if (input.equalsIgnoreCase("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            }
        }

        scanner.close();  // Close scanner
    }
}
