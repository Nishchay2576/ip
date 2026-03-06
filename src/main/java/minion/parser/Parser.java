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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
     * Processes a "todo" command by extracting the description and adding a new Todo task.
     * Validates that the description is not empty, creates the task, updates the task list,
     * provides UI feedback, and saves the changes to storage.
     *
     * @param input   The full raw user input string (e.g., "todo read book").
     * @param tasks   The TaskList object where the new task will be stored.
     * @param ui      The Ui object used to display success or error messages to the user.
     * @param storage The Storage object used to persist the updated task list to the hard drive.
     * @throws MinionException If the todo description is empty or invalid.
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
     * Processes the deadline command by extracting description and date/time info.
     *
     * @param input   The raw user input.
     * @param tasks   The current task list.
     * @param ui      The user interface for feedback.
     * @param storage The storage for saving changes.
     * @throws MinionException If input formats are incorrect.
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

        // Smart Parsing for Hybrid Model
        LocalDate byDate = parseDate(by);
        LocalTime byTime = parseTime(by);
        LocalDateTime byDateTime = parseDateTime(by);

        Deadline newDeadline = new Deadline(description, by, byDate, byTime, byDateTime);
        tasks.addTask(newDeadline);
        showAddFeedback(newDeadline, tasks, ui);
        save(tasks, storage, ui);
    }

    /**
     * Processes the event command by extracting description and start/end timings.
     *
     * @param input   The raw user input.
     * @param tasks   The current task list.
     * @param ui      The user interface for feedback.
     * @param storage The storage for saving changes.
     * @throws MinionException If delimiters are missing or parts are empty.
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

        // Smart Parsing for start and end times
        Event newEvent = new Event(description, from, to,
                parseDate(from), parseTime(from), parseDateTime(from),
                parseDate(to), parseTime(to), parseDateTime(to));

        tasks.addTask(newEvent);
        showAddFeedback(newEvent, tasks, ui);
        save(tasks, storage, ui);
    }

    /**
     * Marks a specific task in the list as completed.
     * Validates the task index, checks if the task is already marked to avoid redundant operations,
     * updates the task status, and saves the updated list to storage.
     *
     * @param input   The raw user command (e.g., "mark 1").
     * @param tasks   The TaskList containing the tasks to be modified.
     * @param ui      The Ui object used to display the success message or error.
     * @param storage The Storage object used to persist the change to the data file.
     * @throws MinionException If the index is invalid or the task is already completed.
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
     * Unmarks a specific task in the list, setting its status to not completed.
     * Ensures the index is valid and the task is currently marked as done before
     * reverting its status and saving the changes.
     *
     * @param input   The raw user command (e.g., "unmark 1").
     * @param tasks   The TaskList containing the tasks to be modified.
     * @param ui      The Ui object used to provide feedback to the user.
     * @param storage The Storage object for data persistence.
     * @throws MinionException If the index is out of bounds or the task is already unmarked.
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
     * Deletes a specific task from the task list based on the provided index.
     * Reconstructs the task list after removal, displays a confirmation message with the
     * removed task details and new total count, and updates the storage file.
     *
     * @param input   The raw user command (e.g., "delete 2").
     * @param tasks   The TaskList from which the task will be removed.
     * @param ui      The Ui object used to display the deletion feedback.
     * @param storage The Storage object to save the shortened task list.
     * @throws MinionException If the index provided is invalid or non-numeric.
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
     * Parses and validates the integer index provided in user commands.
     * Checks that the input contains exactly one argument, that the argument is a valid
     * integer, and that the integer falls within the current bounds of the task list.
     *
     * @param input The full user command string to be parsed.
     * @param tasks The current TaskList used to validate the range of the index.
     * @return The validated 1-based integer index provided by the user.
     * @throws MinionException If the format is incorrect, not an integer, or out of list bounds.
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
     * Attempts to parse a string into a LocalDate object.
     *
     * @param input The raw string to be parsed (expected format: yyyy-MM-dd).
     * @return A LocalDate object if successful, or null if the format is invalid.
     */
    private static LocalDate parseDate(String input) {
        try {
            return LocalDate.parse(input);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Attempts to parse a string into a LocalTime object.
     *
     * @param input The raw string to be parsed (expected format: HH:mm).
     * @return A LocalTime object if successful, or null if the format is invalid.
     */
    private static LocalTime parseTime(String input) {
        try {
            return LocalTime.parse(input);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Attempts to parse a string into a LocalDateTime object.
     * Handles inputs with spaces by replacing them with 'T' for ISO compatibility.
     *
     * @param input The raw string to be parsed (expected format: yyyy-MM-dd HH:mm).
     * @return A LocalDateTime object if successful, or null if the format is invalid.
     */
    private static LocalDateTime parseDateTime(String input) {
        try {
            // Standardizes "yyyy-MM-dd HH:mm" to "yyyy-MM-ddTHH:mm"
            return LocalDateTime.parse(input.trim().replace(" ", "T"));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Displays a standardized confirmation message to the user after a task is successfully added.
     * The message includes the details of the added task and the updated total task count.
     *
     * @param task  The specific Task object that was just added (e.g., Todo, Deadline, or Event).
     * @param tasks The TaskList containing the current collection of tasks, used to retrieve the new size.
     * @param ui    The Ui object used to format and print the feedback message to the console.
     */
    private static void showAddFeedback(Task task, TaskList tasks, Ui ui) {
        String feedback = MinionResponses.MESSAGE_ADD_SUCCESS
                + "\t    " + task.toString() + "\n"
                + MinionResponses.getTaskCountMessage(tasks.getSize());
        ui.showMessage(feedback);
    }

    /**
     * Synchronizes the current in-memory task list with the local storage file.
     * If a persistence error occurs (e.g., file permissions or disk space issues),
     * a "Bido" error message is displayed to the user via the UI.
     *
     * @param tasks   The TaskList containing the current data to be persisted.
     * @param storage The Storage object responsible for writing data to the hard drive.
     * @param ui      The Ui object used to report any I/O errors that occur during the save process.
     */
    private static void save(TaskList tasks, Storage storage, Ui ui) {
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            ui.showError("\t  Bido! I couldn't save your data to the disk.");
        }
    }
}