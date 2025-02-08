package arin.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {

    private LocalDate by;

    /**
     * Creates a Deadline task with a specified description and due date.
     *
     * @param description The description of the task.
     * @param by The due date in "yyyy-MM-dd" format.
     */
    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);
        this.by = LocalDate.parse(by, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * Returns a formatted string representation of the deadline task.
     *
     * @return The formatted string representation.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }

    /**
     * Returns the string format for saving the task.
     *
     * @return The formatted string to save the task.
     */
    @Override
    public String toSaveString() {
        return "D | " + (isDone() ? "1" : "0") + " | " + description + " | " + by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * Gets the due date of the task.
     *
     * @return The due date as a LocalDate object.
     */
    public LocalDate getBy() {
        return by;
    }
}
