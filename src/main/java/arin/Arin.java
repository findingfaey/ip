package arin;

import arin.command.Command;
import arin.storage.Storage;
import arin.task.TaskList;
import arin.ui.Parser;
import arin.ui.Ui;

/**
 * Represents the main chatbot application, Arin.
 * It manages user interactions, executes commands, and maintains task data.
 */
public class Arin {
    private static final String FILE_PATH = "./data/arin.txt";
    private Ui ui;
    private Storage storage;
    private TaskList taskList;

    /**
     * Initializes the chatbot with the specified storage file path.
     *
     * @param filePath The file path for storing task data.
     */
    public Arin(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = new TaskList(storage.loadTasks());
    }

    /**
     * Runs the chatbot, handling user commands in a loop until exit is requested.
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
     * Gets the Ui instance of the chatbot.
     *
     * @return The Ui instance.
     */
    public Ui getUi() {
        return ui;
    }

    /**
     * The main entry point of the chatbot application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Arin("data/arin.txt").run();
    }
}
