package arin.task;

import arin.TaskType;
import arin.task.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    private LocalDate by;

    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);
        this.by = LocalDate.parse(by, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }

    @Override
    public String toSaveString() {
        return "D | " + (isDone() ? "1" : "0") + " | " + description + " | " + by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public LocalDate getBy() {
        return by;
    }
}
