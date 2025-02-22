package arin.task;

import arin.ui.Ui;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

/**
 * Represents a list of tasks.
 * Uses Java Streams API for efficient task filtering and operations.
 */
public class TaskList {

    private ArrayList<Task> tasks;

    /**
     * Creates a TaskList with an existing list of tasks.
     *
     * @param tasks The list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the task list.
     *
     * @param index The index of the task to remove.
     */
    public void deleteTask(int index) {
        tasks.remove(index);
    }

    /**
     * Gets the list of tasks.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Gets a task from the task list by index.
     *
     * @param index The index of the task.
     * @return The task at the specified index.
     */
    public Task getTask(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds!";
        return tasks.get(index);
    }

    /**
     * Marks a task as done.
     *
     * @param index The index of the task to mark as done.
     */
    public void markTaskAsDone(int index) {
        tasks.get(index).markAsDone();
    }

    /**
     * Lists all tasks in the task list.
     *
     * @param ui The UI object used for displaying tasks.
     */
    public void listTasks(Ui ui) {
        if (tasks.isEmpty()) {
            ui.showError("No tasks to display!");
        } else {
            // Pass the entire list to UI to format as one response
            ui.showTaskList(tasks);
        }
    }

    /**
     * Finds tasks that contain the given keyword.
     * Uses Java Streams to filter tasks efficiently.
     *
     * @param keyword The keyword to search for in task descriptions.
     * @return A list of matching tasks.
     */
    public List<Task> findTasks(String keyword) {
        assert keyword != null : "Search keyword cannot be null";

        return tasks.stream()
                .filter(task -> task.getDescription().toLowerCase()
                        .contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Marks a task as not done.
     *
     * @param index The index of the task to mark as not done.
     */
    public void markTaskAsNotDone(int index) {
        tasks.get(index).markAsNotDone();
    }

    /**
     * Gets all completed tasks.
     *
     * @return A list of completed tasks.
     */
    public List<Task> getCompletedTasks() {
        return tasks.stream()
                .filter(Task::isDone)
                .collect(Collectors.toList());
    }

    /**
     * Gets all incomplete tasks.
     *
     * @return A list of incomplete tasks.
     */
    public List<Task> getIncompleteTasks() {
        return tasks.stream()
                .filter(task -> !task.isDone())
                .collect(Collectors.toList());
    }

    /**
     * Gets all tasks of a specific type.
     *
     * @param taskType The type of task to filter by.
     * @return A list of tasks of the specified type.
     */
    public List<Task> getTasksByType(TaskType taskType) {
        assert taskType != null : "Task type cannot be null";

        return tasks.stream()
                .filter(task -> task.taskType == taskType)
                .collect(Collectors.toList());
    }

    /**
     * Gets all upcoming deadline tasks (for future dates).
     *
     * @return A list of upcoming deadline tasks.
     */
    public List<Task> getUpcomingDeadlines() {
        LocalDateTime now = LocalDateTime.now();

        return tasks.stream()
                .filter(task -> task.taskType == TaskType.DEADLINE)
                .map(task -> (Deadline) task)
                .filter(deadline -> deadline.getBy().isAfter(now))
                .collect(Collectors.toList());
    }

    /**
     * Gets all upcoming event tasks (for future dates).
     *
     * @return A list of upcoming event tasks.
     */
    public List<Task> getUpcomingEvents() {
        LocalDateTime now = LocalDateTime.now();

        return tasks.stream()
                .filter(task -> task.taskType == TaskType.EVENT)
                .map(task -> (Event) task)
                .filter(event -> event.getFrom().isAfter(now))
                .collect(Collectors.toList());
    }

    /**
     * Sorts tasks by their description alphabetically.
     *
     * @return A new list of tasks sorted alphabetically by description.
     */
    public List<Task> getSortedByDescription() {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getDescription, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    /**
     * Sorts tasks by their deadline (for Deadline tasks).
     * Non-deadline tasks will appear at the end of the list.
     *
     * @return A sorted list with deadline tasks ordered by due date.
     */
    public List<Task> getSortedByDeadline() {
        // First collect deadline tasks
        List<Deadline> deadlines = tasks.stream()
                .filter(task -> task.taskType == TaskType.DEADLINE)
                .map(task -> (Deadline) task)
                .sorted(Comparator.comparing(Deadline::getBy))
                .collect(Collectors.toList());

        // Then collect non-deadline tasks
        List<Task> nonDeadlines = tasks.stream()
                .filter(task -> task.taskType != TaskType.DEADLINE)
                .collect(Collectors.toList());

        // Combine both lists
        List<Task> result = new ArrayList<>(deadlines);
        result.addAll(nonDeadlines);
        return result;
    }

    /**
     * Gets tasks containing any of the given keywords.
     *
     * @param keywords An array of keywords to search for.
     * @return A list of tasks matching any of the keywords.
     */
    public List<Task> findTasksWithAnyKeyword(String... keywords) {
        assert keywords != null && keywords.length > 0 : "At least one keyword must be provided";

        return tasks.stream()
                .filter(task -> {
                    String description = task.getDescription().toLowerCase();
                    return java.util.Arrays.stream(keywords)
                            .map(String::toLowerCase)
                            .anyMatch(description::contains);
                })
                .collect(Collectors.toList());
    }

    /**
     * Gets tasks containing all of the given keywords.
     *
     * @param keywords An array of keywords to search for.
     * @return A list of tasks matching all of the keywords.
     */
    public List<Task> findTasksWithAllKeywords(String... keywords) {
        assert keywords != null && keywords.length > 0 : "At least one keyword must be provided";

        return tasks.stream()
                .filter(task -> {
                    String description = task.getDescription().toLowerCase();
                    return java.util.Arrays.stream(keywords)
                            .map(String::toLowerCase)
                            .allMatch(description::contains);
                })
                .collect(Collectors.toList());
    }

    /**
     * Gets the count of each type of task.
     *
     * @return An array of counts [todoCount, deadlineCount, eventCount].
     */
    public int[] getTaskTypeCounts() {
        return new int[] {
                (int) tasks.stream().filter(task -> task.taskType == TaskType.TODO).count(),
                (int) tasks.stream().filter(task -> task.taskType == TaskType.DEADLINE).count(),
                (int) tasks.stream().filter(task -> task.taskType == TaskType.EVENT).count()
        };
    }

    /**
     * Gets the count of tasks with a specific status.
     *
     * @param isDone True to count completed tasks, false for incomplete tasks.
     * @return The count of tasks with the specified status.
     */
    public int getTaskCountByStatus(boolean isDone) {
        return (int) tasks.stream()
                .filter(task -> task.isDone() == isDone)
                .count();
    }

    /**
     * Gets tasks due within the specified number of days.
     *
     * @param days Number of days from now.
     * @return List of deadline tasks due within the specified days.
     */
    public List<Task> getTasksDueWithinDays(int days) {
        assert days >= 0 : "Days must be a non-negative integer";

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cutoff = now.plusDays(days);

        return tasks.stream()
                .filter(task -> task.taskType == TaskType.DEADLINE)
                .map(task -> (Deadline) task)
                .filter(deadline -> {
                    LocalDateTime by = deadline.getBy();
                    return by.isAfter(now) && by.isBefore(cutoff);
                })
                .map(deadline -> (Task) deadline)
                .collect(Collectors.toList());
    }

    /**
     * Gets tasks grouped by their type.
     *
     * @return A map of TaskType to list of tasks of that type.
     */
    public java.util.Map<TaskType, List<Task>> getTasksGroupedByType() {
        return tasks.stream()
                .collect(Collectors.groupingBy(task -> task.taskType));
    }

    /**
     * Gets a summary of tasks by status (completed vs. incomplete).
     *
     * @return A map with true->completed tasks and false->incomplete tasks.
     */
    public java.util.Map<Boolean, List<Task>> getTasksGroupedByStatus() {
        return tasks.stream()
                .collect(Collectors.groupingBy(Task::isDone));
    }
}