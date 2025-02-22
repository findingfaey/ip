package arin.command;

import arin.ArinException;
import arin.storage.Storage;
import arin.task.TaskList;
import arin.ui.Ui;

/**
 * Represents a command to display help information about available commands.
 */
public class HelpCommand implements Command {

    /**
     * Executes the help command to display all available commands and their usage.
     *
     * @param taskList The task list (not used in this command).
     * @param ui       The UI to display the help message.
     * @param storage  The storage (not used in this command).
     * @throws ArinException If there is an error during execution.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws ArinException {
        String helpMessage = "Here are the available commands:\n\n"
                + "1. Adding Tasks:\n"
                + "   • todo <description>\n"
                + "   • deadline <description> /by YYYY-MM-DD HHmm\n"
                + "   • event <description> /from YYYY-MM-DD HHmm /to YYYY-MM-DD HHmm\n\n"
                + "2. Managing Tasks:\n"
                + "   • list - View all tasks\n"
                + "   • mark <task number> - Mark a task as done\n"
                + "   • unmark <task number> - Mark a task as not done\n"
                + "   • delete <task number> - Delete a task\n\n"
                + "3. Finding Tasks:\n"
                + "   • find <keyword> - Find tasks containing keyword\n"
                + "   • upcoming [days] - Show tasks due within specified days (default: 7)\n\n"
                + "4. Sorting Tasks:\n"
                + "   • sort by date - Sort tasks chronologically\n"
                + "   • sort by name - Sort tasks alphabetically\n"
                + "   • sort by type - Sort by task type (ToDo → Deadline → Event)\n"
                + "   • sort by status - Sort by completion status\n\n"
                + "5. Other Commands:\n"
                + "   • help - Show this help message\n"
                + "   • bye - Exit the application";

        ui.showHelp(helpMessage);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
