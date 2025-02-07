/**
 * Represents a ToDo task, a subclass of Task.
 */
class ToDo extends Task {
    /**
     * Constructs a ToDo task with the given description.
     *
     * @param description The description of the task.
     */
    public ToDo(String description) {
        super(description, TaskType.TODO);  // Set the task type
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
