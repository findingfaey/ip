import java.util.ArrayList;
import java.util.Scanner;

public class Arin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> tasks = new ArrayList<>();

        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Arin.");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks.get(i));
                }
                System.out.println("____________________________________________________________");
            } else {
                tasks.add(input);
                System.out.println("____________________________________________________________");
                System.out.println(" added: " + input);
                System.out.println("____________________________________________________________");
            }
        }

        scanner.close();
    }
}
