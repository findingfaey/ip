package arin;

/**
 * Command to exit the program.
 */
public class ExitCommand implements Command {

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws ArinException {
        ui.showExit();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
