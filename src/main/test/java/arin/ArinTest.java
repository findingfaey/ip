package arin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import arin.command.Command;
import arin.command.AddTaskCommand;
import arin.command.ExitCommand;
import arin.ui.Arin;
import arin.ui.Parser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ArinTest {
    private Arin arin;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream)); // Redirect System.out for testing

        arin = new Arin("data/arin.txt"); // Initialize Arin with a test file path
    }

    @Test
    void testWelcomeMessage() {
        arin.getUi().showWelcome();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Hello! I'm Arin."));
        assertTrue(output.contains("What can I do for you?"));
    }

    @Test
    void testCommandParsing_Todo() throws ArinException {
        Command command = Parser.parse("todo Read book");
        assertNotNull(command);
        assertTrue(command instanceof AddTaskCommand);
    }

    @Test
    void testCommandParsing_Deadline() throws ArinException {
        Command command = Parser.parse("deadline Submit report /by 2025-12-10");
        assertNotNull(command);
        assertTrue(command instanceof AddTaskCommand);
    }

    @Test
    void testCommandParsing_InvalidCommand() {
        Exception exception = assertThrows(ArinException.class, () -> {
            Parser.parse("invalid command");
        });
        assertTrue(exception.getMessage().contains("I'm sorry, but I don't know what that means"));
    }

    @Test
    void testExitCommand() throws ArinException {
        Command command = Parser.parse("bye");
        assertNotNull(command);
        assertTrue(command instanceof ExitCommand);
        assertTrue(command.isExit());
    }
}
