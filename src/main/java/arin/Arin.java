package arin;

import arin.command.Command;
import arin.storage.Storage;
import arin.task.TaskList;
import arin.ui.Parser;
import arin.ui.Ui;

public class Arin {
    private static final String FILE_PATH = "./data/arin.txt";
    private Ui ui;
    private Storage storage;
    private TaskList taskList;

    public Arin(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = new TaskList(storage.loadTasks());
    }

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

    public static void main(String[] args) {
        new Arin("data/arin.txt").run();
    }
}
