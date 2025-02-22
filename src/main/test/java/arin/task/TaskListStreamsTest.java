package arin.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Test class for testing stream operations in TaskList.
 * This covers the Java 8 Streams implementation (A-Streams).
 */
public class TaskListStreamsTest {

    private TaskList taskList;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    // Create future dates for testing
    private final String futureDateStr1 = LocalDateTime.now().plusDays(1).format(FORMATTER);
    private final String futureDateStr2 = LocalDateTime.now().plusDays(5).format(FORMATTER);
    private final String futureDateStr3 = LocalDateTime.now().plusDays(10).format(FORMATTER);

    /**
     * Sets up the TaskList with sample tasks before each test.
     */
    @BeforeEach
    public void setUp() {
        ArrayList<Task> tasks = new ArrayList<>();

        // Add some test tasks with various states
        ToDo todo1 = new ToDo("Read Java book");
        ToDo todo2 = new ToDo("Practice streams");
        todo2.markAsDone();

        Deadline deadline1 = new Deadline("Submit assignment", futureDateStr1);
        Deadline deadline2 = new Deadline("Complete project", futureDateStr2);
        deadline2.markAsDone();
        Deadline deadline3 = new Deadline("Submit final report", futureDateStr3);

        Event event1 = new Event("Team meeting", futureDateStr1, futureDateStr1.replace("1400", "1600"));
        Event event2 = new Event("Conference", futureDateStr3, futureDateStr3.replace("0900", "1700"));

        // Add all tasks to the list
        tasks.add(todo1);
        tasks.add(todo2);
        tasks.add(deadline1);
        tasks.add(deadline2);
        tasks.add(deadline3);
        tasks.add(event1);
        tasks.add(event2);

        taskList = new TaskList(tasks);
    }

    @Test
    public void testFindTasks_caseSensitivity() {
        // The updated findTasks method should be case-insensitive
        List<Task> results1 = taskList.findTasks("java");
        List<Task> results2 = taskList.findTasks("JAVA");

        assertEquals(1, results1.size(), "Should find 1 task with 'java'");
        assertEquals(1, results2.size(), "Should find same task with 'JAVA' (case insensitive)");
        assertEquals(results1.get(0), results2.get(0), "Should find the same task regardless of case");
    }

    @Test
    public void testGetCompletedTasks() {
        List<Task> completedTasks = taskList.getCompletedTasks();

        assertEquals(2, completedTasks.size(), "Should find 2 completed tasks");
        assertTrue(completedTasks.stream().allMatch(Task::isDone), "All tasks should be marked as done");
    }

    @Test
    public void testGetIncompleteTasks() {
        List<Task> incompleteTasks = taskList.getIncompleteTasks();

        assertEquals(5, incompleteTasks.size(), "Should find 5 incomplete tasks");
        assertTrue(incompleteTasks.stream().noneMatch(Task::isDone), "No tasks should be marked as done");
    }

    @Test
    public void testGetTasksByType() {
        List<Task> todos = taskList.getTasksByType(TaskType.TODO);
        List<Task> deadlines = taskList.getTasksByType(TaskType.DEADLINE);
        List<Task> events = taskList.getTasksByType(TaskType.EVENT);

        assertEquals(2, todos.size(), "Should find 2 ToDo tasks");
        assertEquals(3, deadlines.size(), "Should find 3 Deadline tasks");
        assertEquals(2, events.size(), "Should find 2 Event tasks");
    }

    @Test
    public void testGetUpcomingDeadlines() {
        List<Task> upcomingDeadlines = taskList.getUpcomingDeadlines();

        assertEquals(3, upcomingDeadlines.size(), "Should find 3 upcoming deadlines");
        assertTrue(upcomingDeadlines.stream().allMatch(task -> task instanceof Deadline),
                "All tasks should be Deadline instances");
    }

    @Test
    public void testGetSortedByDescription() {
        List<Task> sorted = taskList.getSortedByDescription();

        assertEquals(7, sorted.size(), "Should contain all tasks");

        // Check if the list is sorted alphabetically
        for (int i = 0; i < sorted.size() - 1; i++) {
            String current = sorted.get(i).getDescription().toLowerCase();
            String next = sorted.get(i + 1).getDescription().toLowerCase();
            assertTrue(current.compareTo(next) <= 0,
                    "Tasks should be sorted alphabetically by description");
        }
    }

    @Test
    public void testFindTasksWithAnyKeyword() {
        List<Task> results = taskList.findTasksWithAnyKeyword("book", "meeting");

        assertEquals(2, results.size(), "Should find 2 tasks with either 'book' or 'meeting'");
    }

    @Test
    public void testFindTasksWithAllKeywords() {
        // Add a task with multiple keywords
        taskList.addTask(new ToDo("Java programming book exercises"));

        List<Task> results = taskList.findTasksWithAllKeywords("java", "book");

        assertEquals(1, results.size(), "Should find 1 task with both 'java' and 'book'");
        assertEquals("Java programming book exercises", results.get(0).getDescription());
    }

    @Test
    public void testGetTasksDueWithinDays() {
        List<Task> tasksWithin3Days = taskList.getTasksDueWithinDays(3);

        assertEquals(1, tasksWithin3Days.size(), "Should find 1 task due within 3 days");

        List<Task> tasksWithin7Days = taskList.getTasksDueWithinDays(7);
        assertEquals(2, tasksWithin7Days.size(), "Should find 2 tasks due within 7 days");
    }

    @Test
    public void testGetTasksGroupedByType() {
        Map<TaskType, List<Task>> groupedByType = taskList.getTasksGroupedByType();

        assertEquals(3, groupedByType.size(), "Should have 3 task types");
        assertEquals(2, groupedByType.get(TaskType.TODO).size(), "Should have 2 ToDo tasks");
        assertEquals(3, groupedByType.get(TaskType.DEADLINE).size(), "Should have 3 Deadline tasks");
        assertEquals(2, groupedByType.get(TaskType.EVENT).size(), "Should have 2 Event tasks");
    }

    @Test
    public void testGetTasksGroupedByStatus() {
        Map<Boolean, List<Task>> groupedByStatus = taskList.getTasksGroupedByStatus();

        assertEquals(2, groupedByStatus.size(), "Should have 2 status groups");
        assertEquals(2, groupedByStatus.get(true).size(), "Should have 2 completed tasks");
        assertEquals(5, groupedByStatus.get(false).size(), "Should have 5 incomplete tasks");
    }

    @Test
    public void testGetTaskTypeCounts() {
        int[] counts = taskList.getTaskTypeCounts();

        assertEquals(3, counts.length, "Should have counts for 3 task types");
        assertEquals(2, counts[0], "Should have 2 ToDo tasks");
        assertEquals(3, counts[1], "Should have 3 Deadline tasks");
        assertEquals(2, counts[2], "Should have 2 Event tasks");
    }

    @Test
    public void testGetTaskCountByStatus() {
        int completedCount = taskList.getTaskCountByStatus(true);
        int incompleteCount = taskList.getTaskCountByStatus(false);

        assertEquals(2, completedCount, "Should have 2 completed tasks");
        assertEquals(5, incompleteCount, "Should have 5 incomplete tasks");
        assertEquals(7, completedCount + incompleteCount, "Total should match overall task count");
    }
}
