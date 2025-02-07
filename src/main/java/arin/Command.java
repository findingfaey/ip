package arin;

/**
 * Represents a command that can be executed.
 */
public interface Command {
    void execute(TaskList taskList, Ui ui, Storage storage) throws ArinException;
    boolean isExit();
}
