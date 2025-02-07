/**
 * Represents an Event task, a subclass of Task.
 */
class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructs an Event task with the given description, start time, and end time.
     *
     * @param description The description of the task.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public Event(String description, String from, String to) {
        super(description, TaskType.EVENT);  // Set the task type
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}