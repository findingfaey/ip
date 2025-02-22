package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.Deadline;
import arin.task.Event;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortCommandTest {
    private Ui ui;
    private TaskList taskList;
    private Storage storage;
    private ByteArrayOutputStream outputStream;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    // Create dates for testing
    private final String dateStr1 = "2025-03-01 1400";
    private final String dateStr2 = "2025-03-15 2359";
    private final String dateStr3 = "2025-04-10 0900";

    @BeforeEach
    public void setUp(@TempDir Path tempDir) {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        storage = new Storage(tempDir.resolve("test_arin.txt").toString());

        // Create test tasks
        ArrayList<Task> tasks = new ArrayList<>();

        // Add tasks in a specific order to test sorting
        ToDo todo1 = new ToDo("Read Java book");
        ToDo todo2 = new ToDo("Practice programming");
        todo2.markAsDone();

        Deadline deadline1 = new Deadline("Submit project", dateStr2);
        Deadline deadline2 = new Deadline("Complete assignment", dateStr1);

        Event event1 = new Event("Team meeting", dateStr2, dateStr2.replace("2359", "2200"));

        // Add all tasks to the list
        tasks.add(todo1);        // [0] - ToDo, not done
        tasks.add(deadline1);    // [1] - Deadline (Mar 15), not done
        tasks.add(event1);       // [2] - Event (Mar 15), not done
        tasks.add(todo2);        // [3] - ToDo, done
        tasks.add(deadline2);    // [4] - Deadline (Mar 1), not done

        taskList = new TaskList(tasks);
        ui = new Ui(taskList, storage);
    }

    @Test
    public void execute_sortByDate_showsTasksSortedByDate() throws ArinException {
        // Create and execute sort command
        SortCommand sortCommand = new SortCommand("date");
        sortCommand.execute(taskList, ui, storage);

        String output = outputStream.toString();

        // Check if output mentions sorting by date
        assertTrue(output.contains("sorted by date"), "Output should mention sorting by date");

        // Basic ordering check - earlier deadline should come before later ones
        int indexDeadline1 = output.indexOf("Complete assignment");
        int indexDeadline2 = output.indexOf("Submit project");
        assertTrue(indexDeadline1 < indexDeadline2,
                "Earlier deadline should appear before later deadline");
    }

    @Test
    public void execute_sortByName_showsTasksSortedAlphabetically() throws ArinException {
        // Create and execute sort command
        SortCommand sortCommand = new SortCommand("name");
        sortCommand.execute(taskList, ui, storage);

        String output = outputStream.toString();

        // Check if output mentions sorting by description
        assertTrue(output.contains("sorted by description"),
                "Output should mention sorting by description");

        // Tasks should be in alphabetical order of descriptions
        int indexTask1 = output.indexOf("Complete assignment");
        int indexTask2 = output.indexOf("Practice programming");
        int indexTask3 = output.indexOf("Read Java book");

        assertTrue(indexTask1 < indexTask2, "Complete should come before Practice alphabetically");
        assertTrue(indexTask2 < indexTask3, "Practice should come before Read alphabetically");
    }

    @Test
    public void execute_sortByType_showsTasksSortedByType() throws ArinException {
        // Create and execute sort command
        SortCommand sortCommand = new SortCommand("type");
        sortCommand.execute(taskList, ui, storage);

        String output = outputStream.toString();

        // Check if output mentions sorting by type
        assertTrue(output.contains("sorted by task type"),
                "Output should mention sorting by task type");

        // First ToDo, then Deadline, then Event
        int firstTodo = output.indexOf("[T]");
        int firstDeadline = output.indexOf("[D]");
        int firstEvent = output.indexOf("[E]");

        assertTrue(firstTodo < firstDeadline, "ToDo tasks should come before Deadline tasks");
        assertTrue(firstDeadline < firstEvent, "Deadline tasks should come before Event tasks");
    }

    @Test
    public void execute_sortByStatus_showsTasksSortedByCompletionStatus() throws ArinException {
        // Create and execute sort command
        SortCommand sortCommand = new SortCommand("status");
        sortCommand.execute(taskList, ui, storage);

        String output = outputStream.toString();

        // Check if output mentions sorting by status
        assertTrue(output.contains("sorted by completion status"),
                "Output should mention sorting by completion status");

        // Not done tasks should come before done tasks
        int firstNotDone = output.indexOf("[ ]");
        int firstDone = output.indexOf("[X]");

        assertTrue(firstNotDone < firstDone,
                "Not done tasks should come before done tasks");
    }

    @Test
    public void isExit_returnsFalse() {
        SortCommand sortCommand = new SortCommand("date");
        assertFalse(sortCommand.isExit(), "SortCommand should not be an exit command");
    }
}
