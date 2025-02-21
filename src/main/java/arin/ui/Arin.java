package arin.ui;

import arin.ArinException;
import arin.command.Command;
import arin.storage.Storage;
import arin.task.TaskList;

/**
 * Represents the main chatbot application, Arin.
 * It manages user interactions, executes commands, and maintains task data.
 */
public class Arin {

    private static final String FILE_PATH = "./data/arin.txt";
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

    /**
     * Initializes the chatbot with the specified storage file path.
     *
     * @param filePath The file path for storing task data.
     */
    public Arin(String filePath) {
        this.storage = new Storage(filePath);
        this.taskList = new TaskList(storage.loadTasks());
        this.ui = new Ui(taskList, storage);
    }

    /**
     * Sets the UI mode to GUI.
     */
    public void setGuiMode() {
        ui.setGuiMode();
    }

    /**
     * Gets a response for the given user input.
     *
     * @param input The user input string.
     * @return The chatbot's response.
     */
    public String getResponse(String input) {
        return ui.processInput(input);
    }

    /**
     * Runs the chatbot in CLI mode, handling user commands until exit is requested.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            String command = ui.readCommand();
            ui.showLine();
            try {
                Command c = Parser.parse(command);
                c.execute(taskList, ui, storage);
                isExit = c.isExit();
            } catch (ArinException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Gets the UI instance.
     *
     * @return The UI instance.
     */
    public Ui getUi() {
        return ui;
    }

    /**
     * Gets the welcome message.
     *
     * @return The welcome message.
     */
    public String getWelcomeMessage() {
        ui.showWelcome();
        return ui.getLatestResponse();
    }

    /**
     * Main method to run the application in CLI mode.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        new Arin(FILE_PATH).run();
    }
}