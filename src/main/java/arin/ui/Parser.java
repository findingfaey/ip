package arin.ui;

import arin.*;
import arin.command.*;
import arin.task.Deadline;
import arin.task.Event;
import arin.task.ToDo;

/**
 * Parses user input commands into Command objects.
 */
public class Parser {

    /**
     * Parses a user input command and returns the corresponding Command object.
     *
     * @param fullCommand The full user command input.
     * @return The Command object representing the action.
     * @throws ArinException If the command format is invalid.
     */
    public static Command parse(String fullCommand) throws ArinException {
        String[] commandParts = fullCommand.split(" ", 2);
        String commandType = commandParts[0];

        switch (commandType) {
            case "todo":
                return new AddTaskCommand(new ToDo(commandParts[1]));
            case "deadline":
                String[] deadlineParts = commandParts[1].split(" /by ");
                if (deadlineParts.length < 2) {
                    throw new ArinException("Invalid deadline format! Use: deadline <task> /by <due date>");
                }
                return new AddTaskCommand(new Deadline(deadlineParts[0], deadlineParts[1]));
            case "event":
                String[] eventParts = commandParts[1].split(" /from | /to "); // Split the event by '/from' and '/to'
                if (eventParts.length < 3) {
                    throw new ArinException("Invalid event format! Use: event <task> /from <start time> /to <end time>");
                }
                return new AddTaskCommand(new Event(eventParts[0], eventParts[1], eventParts[2]));
            case "mark":
                return new MarkTaskCommand(Integer.parseInt(commandParts[1]));
            case "delete":
                return new DeleteTaskCommand(Integer.parseInt(commandParts[1]));
            case "list":
                return new ListTasksCommand();
            case "bye":
                return new ExitCommand();
            default:
                throw new ArinException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
