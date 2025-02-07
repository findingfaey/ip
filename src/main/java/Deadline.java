/**
 * Represents a Deadline task, a subclass of Task.
 */
class Deadline extends Task {
    protected String by;

    /**
     * Constructs a Deadline task with the given description and due date.
     *
     * @param description The description of the task.
     * @param by The due date of the task.
     */
    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);  // Set the task type
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    // Getter method
    public String getBy() {
        return by;
    }
}
