package minion.ui;

import java.util.ArrayList;
import java.util.Scanner;
import minion.responses.MinionResponses;
import minion.task.TaskList;
import minion.task.Task;

/**
 * Represents the user interface of the Minion chatbot.
 * Handles all interactions with the user, including reading input
 * and displaying messages to the console.
 */
public class Ui {

    /** The scanner used to read user input from the console. */
    private final Scanner scanner;

    /**
     * Initializes the Ui object and its input scanner.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a full line of command input from the user.
     *
     * @return The raw string entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints the standard welcome message when the bot starts.
     */
    public void showWelcome() {
        MinionResponses.printWithLines(MinionResponses.WELCOME_MESSAGE);
    }

    /**
     * Prints the standard goodbye message.
     */
    public void showBye() {
        MinionResponses.printWithLines(MinionResponses.BYE_MESSAGE);
    }

    /**
     * Displays an error message to the user in a standardized format.
     *
     * @param message The error message to be displayed.
     */
    public void showError(String message) {
        MinionResponses.printWithLines(message);
    }

    /**
     * Displays the current list of tasks in a numbered format.
     * If the list is empty, a specific empty-list message is shown.
     *
     * @param tasks The TaskList object containing the tasks to display.
     */
    public void showTaskList(TaskList tasks) {
        if (tasks.isEmpty()) {
            MinionResponses.printWithLines(MinionResponses.MESSAGE_EMPTY_LIST);
            return;
        }

        StringBuilder listOutput = new StringBuilder(MinionResponses.MESSAGE_LIST_HEADER);
        for (int i = 0; i < tasks.getSize(); i++) {
            Task currentTask = tasks.getTask(i);
            listOutput.append("\t  ").append(i + 1).append(". ").append(currentTask.toString());
            if (i < tasks.getSize() - 1) {
                listOutput.append("\n");
            }
        }
        MinionResponses.printWithLines(listOutput.toString());
    }

    /**
     * Displays the tasks that match the user's search keyword.
     *
     *  @param results The list of tasks that matched the search.
     */
    public void showSearchResults(ArrayList<minion.task.Task> results) {
        if (results.isEmpty()) {
            showMessage("\t  Bido! I couldn't find any matching tasks in your list.");
            return;
        }

        StringBuilder resultsBuilder = new StringBuilder("\t  Ba-na-na! Here are the matching tasks in your list:\n");
        for (int i = 0; i < results.size(); i++) {
            resultsBuilder.append("\t  ").append(i + 1).append(". ").append(results.get(i).toString());
            if (i < results.size() - 1) {
                resultsBuilder.append("\n");
            }
        }
        showMessage(resultsBuilder.toString());
    }

    /**
     * Displays a generic message to the user.
     *
     * @param message The text to be printed.
     */
    public void showMessage(String message) {
        MinionResponses.printWithLines(message);
    }
}