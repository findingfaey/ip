package arin.ui;

import arin.*;
import arin.command.*;
import arin.task.Deadline;
import arin.task.Event;
import arin.task.ToDo;

/**
 * Parses user input commands into executable Command objects.
 */
public class Parser {

    /**
     * Parses a user input command and returns the corresponding Command object.
     *
     * @param fullCommand The full user input command.
     * @return The Command object corresponding to the input.
     * @throws ArinException If the input command is not recognized or invalid.
     */
    public static Command parse(String fullCommand) throws ArinException {
        String[] commandParts = fullCommand.split(" ", 2);
        String commandType = commandParts[0];

        switch (commandType) {
        case "todo":
            return new AddTaskCommand(new ToDo(commandParts[1]));
        case "deadline":
            return parseDeadlineCommand(commandParts);
        case "event":
            return parseEventCommand(commandParts);
        case "mark":
            return new MarkTaskCommand(Integer.parseInt(commandParts[1]));
        case "delete":
            return new DeleteTaskCommand(Integer.parseInt(commandParts[1]));
        case "list":
            return new ListTasksCommand();
        case "find":
            return new FindCommand(commandParts[1]);
        case "bye":
            return new ExitCommand();
        default:
            throw new ArinException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Parses the "deadline" command and returns an AddTaskCommand.
     *
     * @param commandParts The parts of the command split by spaces.
     * @return An AddTaskCommand for the deadline task.
     * @throws ArinException If the command format is incorrect.
     */
    private static Command parseDeadlineCommand(String[] commandParts) throws ArinException {
        if (commandParts.length < 2) {
            throw new ArinException("Invalid deadline format! Use: deadline <task> /by <due date>");
        }
        String[] deadlineParts = commandParts[1].split(" /by ");
        if (deadlineParts.length < 2) {
            throw new ArinException("Invalid deadline format! Use: deadline <task> /by <due date>");
        }
        return new AddTaskCommand(new Deadline(deadlineParts[0], deadlineParts[1]));
    }

    /**
     * Parses the "event" command and returns an AddTaskCommand.
     *
     * @param commandParts The parts of the command split by spaces.
     * @return An AddTaskCommand for the event task.
     * @throws ArinException If the command format is incorrect.
     */
    private static Command parseEventCommand(String[] commandParts) throws ArinException {
        if (commandParts.length < 2) {
            throw new ArinException("Invalid event format! Use: event <task> /from <start time> /to <end time>");
        }
        String[] eventParts = commandParts[1].split(" /from | /to ");
        if (eventParts.length < 3) {
            throw new ArinException("Invalid event format! Use: event <task> /from <start time> /to <end time>");
        }
        return new AddTaskCommand(new Event(eventParts[0], eventParts[1], eventParts[2]));
    }

}
