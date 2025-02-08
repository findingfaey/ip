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
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

    /**
     * Initializes the chatbot with the specified storage file path.
     *
     * @param filePath The file path for storing task data.
     */
    public Arin(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.taskList = new TaskList(storage.loadTasks());
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

    public Ui getUi() {
        return ui;
    }

    public static void main(String[] args) {
        new Arin(FILE_PATH).run();
    }
}
