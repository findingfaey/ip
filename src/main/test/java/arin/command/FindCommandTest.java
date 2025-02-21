package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.Task;
import arin.task.TaskList;
import arin.task.ToDo;
import arin.ui.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindCommandTest {
    private Ui ui;
    private TaskList taskList;
    private Storage storage;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp(@TempDir Path tempDir) {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        storage = new Storage(tempDir.resolve("test_arin.txt").toString());

        // Create test tasks
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("read book"));
        tasks.add(new ToDo("watch movie"));
        tasks.add(new ToDo("study for exam"));

        taskList = new TaskList(tasks);
        ui = new Ui(taskList, storage);
    }

    @Test
    public void execute_keywordExists_showsMatchingTasks() throws ArinException {
        // Create and execute a find command
        FindCommand findCommand = new FindCommand("book");
        findCommand.execute(taskList, ui, storage);

        String output = outputStream.toString();
        assertTrue(output.contains("book"), "Output should contain the matching task");
        assertTrue(output.contains("matching tasks"), "Output should mention matching tasks");
    }

    @Test
    public void execute_keywordNotFound_showsNoMatchingTasks() throws ArinException {
        // Create and execute a find command with no matches
        FindCommand findCommand = new FindCommand("xyz");
        findCommand.execute(taskList, ui, storage);

        String output = outputStream.toString();
        assertTrue(output.contains("No matching tasks") ||
                        output.contains("Error: No matching tasks"),
                "Output should indicate no matching tasks");
    }

    @Test
    public void isExit_returnsFalse() {
        FindCommand findCommand = new FindCommand("test");
        assertEquals(false, findCommand.isExit(), "FindCommand should not be an exit command");
    }
}
