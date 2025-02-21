package arin.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a strict deadline (requires date and time).
 */
public class Deadline extends Task {
    private final LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    /**
     * Creates a Deadline task.
     *
     * @param description The description of the deadline.
     * @param by The due date/time in "yyyy-MM-dd HHmm".
     * @throws IllegalArgumentException if the format is incorrect.
     */
    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);
        try {
            this.by = LocalDateTime.parse(by, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Invalid deadline format! Use: deadline <task> /by yyyy-MM-dd HHmm (e.g., '2025-02-21 2359')");
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DISPLAY_FORMATTER) + ")";
    }

    @Override
    public String toSaveString() {
        return "D | " + (isDone() ? "1" : "0") + " | " + description + " | " + by.format(INPUT_FORMATTER);
    }

    public LocalDateTime getBy() {
        return by;
    }
}
