package minion.parser;

import minion.ui.Ui;
import minion.task.TaskList;
import minion.task.Task;
import minion.task.Todo;
import minion.task.Deadline;
import minion.task.Event;
import minion.storage.Storage;
import minion.exception.MinionException;
import minion.responses.MinionResponses;

import java.io.IOException;

/**
 * Deals with making sense of the user command.
 * Parses the raw user input and coordinates the execution of the
 * corresponding logic between the Ui, TaskList, and Storage components.
 */
public class Parser {

    /**
     * Parses the user input and executes the appropriate command.
     *
     * @param input   The full raw input string from the user.
     * @param tasks   The TaskList object containing the current tasks.
     * @param ui      The Ui object for user interaction.
     * @param storage The Storage object for updating the save file.
     * @return True if the "bye" command was issued, false otherwise.
     * @throws MinionException If the command is invalid or parameters are missing.
     */
    public static boolean parse(String input, TaskList tasks, Ui ui, Storage storage)
            throws MinionException {

        String trimmedInput = input.trim();
        String lowerInput = trimmedInput.toLowerCase();

        if (lowerInput.equals("bye")) {
            ui.showBye();
            return true;
        }

        if (lowerInput.equals("list")) {
            ui.showTaskList(tasks);
        } else if (lowerInput.startsWith("mark")) {
            handleMark(trimmedInput, tasks, ui, storage);
        } else if (lowerInput.startsWith("unmark")) {
            handleUnmark(trimmedInput, tasks, ui, storage);
        } else if (lowerInput.startsWith("todo")) {
            handleTodo(trimmedInput, tasks, ui, storage);
        } else if (lowerInput.startsWith("deadline")) {
            handleDeadline(trimmedInput, tasks, ui, storage);
        } else if (lowerInput.startsWith("event")) {
            handleEvent(trimmedInput, tasks, ui, storage);
        } else if (lowerInput.startsWith("delete")) {
            handleDelete(trimmedInput, tasks, ui, storage);
        } else {
            throw new MinionException(MinionResponses.UNKNOWN_COMMAND);
        }

        return false;
    }

    /**
     * Handles the creation and addition of a Todo task.
     */
    private static void handleTodo(String input, TaskList tasks, Ui ui, Storage storage)
            throws MinionException {
        if (input.length() <= 5) { // "todo " is 5 chars
            throw new MinionException(MinionResponses.ERROR_EMPTY_TODO);
        }
        String description = input.substring(5).trim();
        Todo newTodo = new Todo(description);
        tasks.addTask(newTodo);
        showAddFeedback(newTodo, tasks, ui);
        save(tasks, storage, ui);
    }

    /**
     * Handles the creation and addition of a Deadline task.
     */
    private static void handleDeadline(String input, TaskList tasks, Ui ui, Storage storage)
            throws MinionException {
        if (input.length() <= 9) { // "deadline " is 9 chars
            throw new MinionException(MinionResponses.ERROR_EMPTY_DEADLINE);
        }

        int byIndex = input.indexOf("/by");
        if (byIndex == -1) {
            throw new MinionException(MinionResponses.ERROR_MISSING_BY);
        }

        String description = input.substring(9, byIndex).trim();
        String by = input.substring(byIndex + 3).trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new MinionException(MinionResponses.ERROR_DEADLINE_PART_EMPTY);
        }

        Deadline newDeadline = new Deadline(description, by);
        tasks.addTask(newDeadline);
        showAddFeedback(newDeadline, tasks, ui);
        save(tasks, storage, ui);
    }

    /**
     * Handles the creation and addition of an Event task.
     */
    private static void handleEvent(String input, TaskList tasks, Ui ui, Storage storage)
            throws MinionException {
        if (input.length() <= 6) { // "event " is 6 chars
            throw new MinionException(MinionResponses.ERROR_EMPTY_EVENT);
        }

        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");

        if (fromIndex == -1 || toIndex == -1) {
            throw new MinionException(MinionResponses.ERROR_MISSING_EVENT_DELIMITERS);
        }

        if (fromIndex > toIndex) {
            throw new MinionException(MinionResponses.ERROR_EVENT_TIME_ORDER);
        }

        String description = input.substring(6, fromIndex).trim();
        String from = input.substring(fromIndex + 5, toIndex).trim();
        String to = input.substring(toIndex + 3).trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new MinionException(MinionResponses.ERROR_EVENT_PART_EMPTY);
        }

        Event newEvent = new Event(description, from, to);
        tasks.addTask(newEvent);
        showAddFeedback(newEvent, tasks, ui);
        save(tasks, storage, ui);
    }

    /**
     * Handles marking a task as completed.
     */
    private static void handleMark(String input, TaskList tasks, Ui ui, Storage storage)
            throws MinionException {
        int taskIndex = parseIndex(input, tasks) - 1;
        Task task = tasks.getTask(taskIndex);

        if (task.getStatusIcon().equals("X")) {
            throw new MinionException(MinionResponses.ERROR_ALREADY_DONE);
        }

        task.setDone(true);
        ui.showMessage(MinionResponses.MESSAGE_MARK_SUCCESS + "\t    " + task);
        save(tasks, storage, ui);
    }

    /**
     * Handles unmarking a task as not completed.
     */
    private static void handleUnmark(String input, TaskList tasks, Ui ui, Storage storage)
            throws MinionException {
        int taskIndex = parseIndex(input, tasks) - 1;
        Task task = tasks.getTask(taskIndex);

        if (task.getStatusIcon().equals(" ")) {
            throw new MinionException(MinionResponses.ERROR_NOT_DONE_YET);
        }

        task.setDone(false);
        ui.showMessage(MinionResponses.MESSAGE_UNMARK_SUCCESS + "\t    " + task);
        save(tasks, storage, ui);
    }

    /**
     * Handles the deletion of a task.
     */
    private static void handleDelete(String input, TaskList tasks, Ui ui, Storage storage)
            throws MinionException {
        int taskIndex = parseIndex(input, tasks) - 1;
        Task removedTask = tasks.deleteTask(taskIndex);

        String feedback = MinionResponses.MESSAGE_DELETE_SUCCESS
                + "\t    " + removedTask.toString() + "\n"
                + MinionResponses.getTaskCountMessage(tasks.getSize());

        ui.showMessage(feedback);
        save(tasks, storage, ui);
    }

    /**
     * Helper to parse the integer index from user commands like 'delete 1'.
     */
    private static int parseIndex(String input, TaskList tasks) throws MinionException {
        String[] parts = input.split("\\s+");
        if (parts.length != 2) {
            throw new MinionException(MinionResponses.ERROR_INVALID_FORMAT);
        }
        try {
            int index = Integer.parseInt(parts[1]);
            if (index <= 0 || index > tasks.getSize()) {
                throw new MinionException("\t  Invalid task number. You have " + tasks.getSize() + " tasks.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new MinionException(MinionResponses.ERROR_NOT_INT);
        }
    }

    /**
     * Helper to show standardized add feedback via Ui.
     */
    private static void showAddFeedback(Task task, TaskList tasks, Ui ui) {
        String feedback = MinionResponses.MESSAGE_ADD_SUCCESS
                + "\t    " + task.toString() + "\n"
                + MinionResponses.getTaskCountMessage(tasks.getSize());
        ui.showMessage(feedback);
    }

    /**
     * Helper to trigger storage updates.
     */
    private static void save(TaskList tasks, Storage storage, Ui ui) {
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            ui.showError("\t  Bido! I couldn't save your data to the disk.");
        }
    }
}