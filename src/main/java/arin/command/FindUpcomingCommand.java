package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.Task;
import arin.task.TaskList;
import arin.ui.Ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a command to find upcoming deadlines and events.
 */
public class FindUpcomingCommand implements Command {

    private final int days;

    /**
     * Creates a FindUpcomingCommand to find tasks due within the next specified days.
     *
     * @param days The number of days to look ahead.
     */
    public FindUpcomingCommand(final int days) {
        this.days = days;
    }

    /**
     * Executes the command to find upcoming tasks due within the specified days.
     * Uses Java Streams to filter and combine upcoming deadlines and events.
     *
     * @param taskList The task list to search.
     * @param ui       The UI to display matching tasks.
     * @param storage  The storage (not used in this command).
     * @throws ArinException If an error occurs during execution.
     */
    @Override
    public void execute(final TaskList taskList, final Ui ui, final Storage storage) throws ArinException {
        // Get upcoming deadlines within days range
        List<Task> upcomingDeadlines = taskList.getTasksDueWithinDays(days);

        // Get upcoming events within days range
        List<Task> upcomingEvents = taskList.getUpcomingEvents().stream()
                .filter(task -> {
                    // Additional filter to limit to the specified days
                    // Note: The getUpcomingEvents() already filters for future events,
                    // but we need to further filter by the specified number of days
                    java.time.LocalDateTime now = java.time.LocalDateTime.now();
                    java.time.LocalDateTime cutoff = now.plusDays(days);
                    arin.task.Event event = (arin.task.Event) task;
                    return event.getFrom().isBefore(cutoff);
                })
                .collect(java.util.stream.Collectors.toList());

        // Combine the lists
        List<Task> upcomingTasks = new ArrayList<>();
        upcomingTasks.addAll(upcomingDeadlines);
        upcomingTasks.addAll(upcomingEvents);

        // Display the results
        if (upcomingTasks.isEmpty()) {
            ui.showError("No upcoming tasks in the next " + days + " days.");
        } else {
            ui.showMatchingTasks(upcomingTasks);
        }
    }

    /**
     * Indicates whether this command should cause the application to exit.
     *
     * @return false as this command does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}