package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.Task;
import arin.task.TaskList;
import arin.task.ToDo;
import arin.ui.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for FindCommand.
 */
public class FindCommandTest {

    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    /**
     * Sets up test data before each test.
     */
    @BeforeEach
    public void setUp() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("read book"));
        tasks.add(new ToDo("write report"));
        tasks.add(new ToDo("do homework"));
        taskList = new TaskList(tasks);
        ui = new Ui();
        storage = new Storage("test.txt");  // Dummy storage path
    }

    /**
     * Tests execution of FindCommand with a matching keyword.
     */
    @Test
    public void execute_findExistingTask_success() throws ArinException {
        FindCommand command = new FindCommand("book");
        command.execute(taskList, ui, storage);
        List<Task> result = taskList.findTasks("book");
        assertEquals(1, result.size(), "Expected 1 task containing 'book'");
        assertEquals("read book", result.get(0).getDescription());
    }

    /**
     * Tests execution of FindCommand when no tasks match.
     */
    @Test
    public void execute_findNonExistingTask_noMatch() throws ArinException {
        FindCommand command = new FindCommand("exercise");
        command.execute(taskList, ui, storage);
        List<Task> result = taskList.findTasks("exercise");
        assertTrue(result.isEmpty(), "Expected no tasks matching 'exercise'");
    }
}
