package arin.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import arin.*;
import arin.command.*;
import arin.task.Deadline;
import arin.task.Event;
import arin.task.ToDo;

/**
 * Parses user input commands into executable Command objects.
 */
public class Parser {

    private static final String DEADLINE_DELIMITER = " /by ";
    private static final String EVENT_DELIMITER = " /from | /to ";

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
            if (commandParts.length < 2 || commandParts[1].trim().isEmpty()) {
                throw new ArinException("Invalid todo format! Use: todo <task description>");
            }
            return new AddTaskCommand(new ToDo(commandParts[1]));
        case "deadline":
            return parseDeadlineCommand(commandParts);
        case "event":
            return parseEventCommand(commandParts);
        case "mark":
            return new MarkTaskCommand(Integer.parseInt(commandParts[1]));
        case "unmark":
            return new UnmarkTaskCommand(Integer.parseInt(commandParts[1]));
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
            throw new ArinException("Invalid deadline format! Use: deadline <task> /by yyyy-MM-dd HHmm (e.g., '2025-02-21 2359')");
        }

        String[] deadlineParts = commandParts[1].split(DEADLINE_DELIMITER);
        if (deadlineParts.length < 2) {
            throw new ArinException("Invalid deadline format! Use: deadline <task> /by yyyy-MM-dd HHmm (e.g., '2025-02-21 2359')");
        }

        try {
            LocalDateTime.parse(deadlineParts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        } catch (DateTimeParseException e) {
            throw new ArinException("Invalid date format! Use: yyyy-MM-dd HHmm (e.g., '2025-02-21 2359')");
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
        if (commandParts.length < 2 || !commandParts[1].contains("/from ") || !commandParts[1].contains("/to ")) {
            throw new ArinException("Invalid event format! Use: event <task> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm (e.g., '2025-03-05 1400 /to 2025-03-05 1600')");
        }

        String input = commandParts[1];

        // Extract descriptions and times
        int fromIndex = input.indexOf("/from ");
        int toIndex = input.indexOf("/to ");

        if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
            throw new ArinException("Invalid event format! Make sure /from comes before /to.");
        }

        String description = input.substring(0, fromIndex).trim();
        String from = input.substring(fromIndex + 6, toIndex).trim();
        String to = input.substring(toIndex + 4).trim();

        return new AddTaskCommand(new Event(description, from, to));
    }
}
