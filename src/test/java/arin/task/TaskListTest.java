package arin.task;

import arin.ui.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for TaskList.
 */
public class TaskListTest {

    private TaskList taskList;

    /**
     * Sets up the TaskList with sample tasks before each test.
     */
    @BeforeEach
    public void setUp() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("read book"));
        tasks.add(new Deadline("return book", "2025-06-06"));
        tasks.add(new Event("book club meeting", "2025-06-10 1800", "2025-06-10 2000"));
        tasks.add(new ToDo("do homework"));

        taskList = new TaskList(tasks);
    }

    /**
     * Tests finding tasks with an exact match keyword.
     */
    @Test
    public void findTasks_keywordMatch_success() {
        List<Task> result = taskList.findTasks("book");
        assertEquals(3, result.size(), "Expected 3 tasks containing 'book'");
        assertEquals("read book", result.get(0).getDescription());
        assertEquals("return book", result.get(1).getDescription());
        assertEquals("book club meeting", result.get(2).getDescription());
    }

    /**
     * Tests finding tasks with a partial match keyword.
     */
    @Test
    public void findTasks_partialMatch_success() {
        List<Task> result = taskList.findTasks("meet");
        assertEquals(1, result.size(), "Expected 1 task containing 'meet'");
        assertTrue(result.get(0).getDescription().contains("meeting"));
    }

    /**
     * Tests finding tasks with a keyword that does not exist.
     */
    @Test
    public void findTasks_noMatch_emptyList() {
        List<Task> result = taskList.findTasks("exercise");
        assertTrue(result.isEmpty(), "Expected no tasks matching 'exercise'");
    }
}
