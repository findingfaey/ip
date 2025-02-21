// Updated TaskListTest.java
package arin.task;

import arin.ui.Ui;
import arin.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for TaskList.
 */
public class TaskListTest {

    private TaskList taskList;
    private Ui ui;
    private Storage storage;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    /**
     * Sets up the TaskList with sample tasks before each test.
     */
    @BeforeEach
    public void setUp(@TempDir Path tempDir) {
        System.setOut(new PrintStream(outputStream)); // Redirect output

        // Initialize with test tasks
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("read book"));
        tasks.add(new Deadline("return book", "2025-06-06"));
        tasks.add(new Event("book club meeting", "2025-06-10 1800", "2025-06-10 2000"));
        tasks.add(new ToDo("do homework"));

        // Create storage in temp directory
        storage = new Storage(tempDir.resolve("arin-test.txt").toString());
        taskList = new TaskList(tasks);
        ui = new Ui(taskList, storage);
    }

    // Existing tests...

    /**
     * Tests the listTasks method with tasks present.
     */
    @Test
    public void listTasks_withTasks_displaysAllTasks() {
        taskList.listTasks(ui);
        String output = outputStream.toString();

        // Check if all tasks are displayed
        assertTrue(output.contains("read book"), "Output should contain first task");
        assertTrue(output.contains("return book"), "Output should contain second task");
        assertTrue(output.contains("book club meeting"), "Output should contain third task");
        assertTrue(output.contains("do homework"), "Output should contain fourth task");
    }

    /**
     * Tests the listTasks method with an empty list.
     */
    @Test
    public void listTasks_emptyList_displaysEmptyMessage() {
        TaskList emptyList = new TaskList(new ArrayList<>());
        emptyList.listTasks(ui);
        String output = outputStream.toString();

        assertTrue(output.contains("No tasks to display"),
                "Output should indicate no tasks available");
    }

    /**
     * Tests marking a task as done.
     */
    @Test
    public void markTaskAsDone_validIndex_marksTaskDone() {
        taskList.markTaskAsDone(0);
        assertTrue(taskList.getTask(0).isDone(), "Task should be marked as done");
    }

    /**
     * Tests deleting a task.
     */
    @Test
    public void deleteTask_validIndex_removesTask() {
        int initialSize = taskList.getTasks().size();
        Task taskToDelete = taskList.getTask(0);
        taskList.deleteTask(0);

        assertEquals(initialSize - 1, taskList.getTasks().size(),
                "Task list should have one less item");
        assertFalse(taskList.getTasks().contains(taskToDelete),
                "Deleted task should not be in the list");
    }
}
