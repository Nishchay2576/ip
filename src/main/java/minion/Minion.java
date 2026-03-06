package minion;

import minion.ui.Ui;
import minion.task.TaskList;
import minion.storage.Storage;
import minion.parser.Parser;
import minion.exception.MinionException;

/**
 * Represents the main entry point for the Minion chatbot.
 * Coordinates the initialization of UI, Storage, and TaskList components
 * and manages the primary execution loop.
 */
public class Minion {

    /** The storage component responsible for saving and loading tasks. */
    private Storage storage;
    /** The task list component that holds the current tasks. */
    private TaskList tasks;
    /** The user interface component for interacting with the user. */
    private Ui ui;

    /**
     * Initializes the chatbot by setting up the UI, loading tasks from storage,
     * and preparing the TaskList.
     *
     * @param filePath The relative path to the data file where tasks are stored.
     */
    public Minion(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (MinionException e) {
            // If loading fails (e.g., file not found), start with an empty list
            ui.showError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Starts the main execution loop of the chatbot.
     * Continues to read and parse commands until the 'bye' command is issued.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                isExit = Parser.parse(fullCommand, tasks, ui, storage);
            } catch (MinionException e) {
                // Catch and display any chatbot-specific errors
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Main method to launch the Minion application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Minion("data/minion.txt").run();
    }
}