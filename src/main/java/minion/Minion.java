package minion;

import java.util.Scanner;
import java.util.ArrayList;
import minion.task.Task;
import minion.task.Todo;
import minion.task.Deadline;
import minion.task.Event;
import minion.responses.MinionResponses;
import minion.exception.MinionException;


/**
 * Main class for the chatbot Minion.
 * Provides CLI for users to manage todos, deadlines and events.
 */
public class Minion {

    /** Command Constants */
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";

    /** List to store task objects. */
    private static final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Main method of the chatbot.
     * Reads user commands in a loop and routes them properly.
     *
     * @param args (not used)
     */
    public static void main(String[] args) {
        printWelcomeMessage();
        Scanner in = new Scanner(System.in);

        // Main command running loop
        while (true) {
            String userInput = in.nextLine();
            String lowerInput = userInput.toLowerCase(); // Consistent case handling

            try {
                if (lowerInput.equals(COMMAND_BYE)) {
                    printByeMessage();
                    break;
                } else if (lowerInput.equals(COMMAND_LIST)) {
                    handleListInput();
                } else if (lowerInput.startsWith(COMMAND_MARK)) {
                    handleMarkInput(userInput);
                } else if (lowerInput.startsWith(COMMAND_UNMARK)) {
                    handleUnmarkInput(userInput);
                } else if (lowerInput.startsWith(COMMAND_TODO)) {
                    handleTodoInput(userInput);
                } else if (lowerInput.startsWith(COMMAND_DEADLINE)) {
                    handleDeadlineInput(userInput);
                } else if (lowerInput.startsWith(COMMAND_EVENT)) {
                    handleEventInput(userInput);
                } else {
                    throw new MinionException(MinionResponses.UNKNOWN_COMMAND);
                }
            } catch (MinionException e) { // Catch chatbot-specific errors
                MinionResponses.printWithLines(e.getMessage());
            }
        }
    }

    //Helper methods

    /**
     * Check if a string does not contain only numbers.
     *
     * @param str String to be checked.
     * @return True if it is null, empty, or non-numeric, false otherwise.
     */
    private static boolean isNotInteger(String str) {
        // Check if string is null or empty
        if (str == null || str.isEmpty()) {
            return true;
        }

        // Check if string can be parsed as an integer
        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    /**
     * Checks if the user input task number is valid for the user given command.
     * Prints appropriate message if invalid task number is entered.
     *
     * @param parts The user input string split (e.g. {"mark", "1"}).
     * @return -1 if the task number is invalid, and the task number if a valid task number is entered.
     */
    private static int getValidTaskNumber(String[] parts) {
        // Check format
        if (parts.length != 2) {
            MinionResponses.printWithLines(MinionResponses.ERROR_INVALID_FORMAT);
            return -1;
        }

        // Check if it's an integer
        if (isNotInteger(parts[1])) {
            MinionResponses.printWithLines(MinionResponses.ERROR_NOT_INT);
            return -1;
        }

        int taskNumber = Integer.parseInt(parts[1]);
        int totalTasks = tasks.size();

        // Check bounds
        if (taskNumber <= 0 || taskNumber > totalTasks) {
            String errorMessage = "\t  Invalid task number. You have " + totalTasks + " tasks.";
            MinionResponses.printWithLines(errorMessage);
            return -1;
        }

        return taskNumber;
    }

    /**
     * Adds the specified task to the task array and gives user feedback upon adding the task successfully.
     * Tells the user if the tasklist is full already.
     *
     * @param task The task object (including Event, Todo, Deadline) to be stored.
     */
    private static void addTaskToList(Task task) {
        tasks.add(task);

        String feedback = MinionResponses.MESSAGE_ADD_SUCCESS
                + "\t    " + task.toString() + "\n"
                + MinionResponses.getTaskCountMessage(tasks.size());

        MinionResponses.printWithLines(feedback);
    }

    // Welcome and Bye messages

    /**
     * Prints the welcome message of the chatbot.
     * Used at the start of the program.
     */
    private static void printWelcomeMessage() {
        MinionResponses.printWithLines(MinionResponses.WELCOME_MESSAGE);
    }

    /**
     * Prints bye message and terminates the program.
     */
    private static void printByeMessage() {
        MinionResponses.printWithLines(MinionResponses.BYE_MESSAGE);
    }

    // Methods handling different user inputs

    /**
     * Displays the current list of tasks to the user.
     * If the list is empty, prints a message indicating that no tasks have been stored yet.
     */
    private static void handleListInput() {
        // Handle the empty list case first
        if (tasks.isEmpty()) {
            MinionResponses.printWithLines(MinionResponses.MESSAGE_EMPTY_LIST);
            return;
        }

        StringBuilder listOutput = new StringBuilder(MinionResponses.MESSAGE_LIST_HEADER);
        for (int i = 0; i < tasks.size(); i++) {
            Task currentTask = tasks.get(i);
            listOutput.append("\t  ").append(i + 1).append(". ").append(currentTask.toString());
            if (i < tasks.size() - 1) {
                listOutput.append("\n");
            }
        }

        MinionResponses.printWithLines(listOutput.toString());
    }

    /**
     * Processes the mark command to set a specific task as completed.
     * Checks if the task number is valid and also if the task has already been marked.
     *
     * @param userInput The full command string entered by the user (e.g. mark 1).
     */
    private static void handleMarkInput(String userInput) {
        // Split input by one or more whitespace characters
        String[] parts = userInput.split("\\s+");

        // check for valid task number
        int taskNumber = getValidTaskNumber(parts);

        if (taskNumber == -1) {
            return;
        }

        Task taskToMark = tasks.get(taskNumber - 1);

        // Check if already marked
        if (taskToMark.getStatusIcon().equals("X")) {
            MinionResponses.printWithLines(MinionResponses.ERROR_ALREADY_DONE);
            return;
        }

        taskToMark.setDone(true);
        MinionResponses.printWithLines(MinionResponses.MESSAGE_MARK_SUCCESS + "\t    " + taskToMark);
    }

    /**
     * Processes the unmark command to set a specific task as not completed.
     * Checks if the task number is valid and also if the task has not been marked yet.
     *
     * @param userInput The full command string entered by the user (e.g. unmark 1).
     */
    private static void handleUnmarkInput(String userInput) {
        // Split input by one or more whitespace characters
        String[] parts = userInput.split("\\s+");

        // check for valid task number
        int taskNumber = getValidTaskNumber(parts);

        if (taskNumber == -1) {
            return;
        }

        Task taskToUnmark = tasks.get(taskNumber - 1);

        // Check if already marked
        if (taskToUnmark.getStatusIcon().equals(" ")) {
            MinionResponses.printWithLines(MinionResponses.ERROR_NOT_DONE_YET);
            return;
        }

        taskToUnmark.setDone(false);
        MinionResponses.printWithLines(MinionResponses.MESSAGE_UNMARK_SUCCESS + "\t    " + taskToUnmark);
    }

    /**
     * Processes the todo command by extracting the task description.
     * Checks if the description is not empty before creating a new Todo object.
     *
     * @param userInput The full command string entered by the user (e.g. todo read book).
     */
    private static void handleTodoInput(String userInput){
        // check if the input is valid
        int lengthOfStringTodo = COMMAND_TODO.length();
        if (userInput.trim().length() == lengthOfStringTodo) {
            MinionResponses.printWithLines(MinionResponses.ERROR_EMPTY_TODO);
            return;
        }

        String description = userInput.substring(lengthOfStringTodo + 1).trim();
        addTaskToList(new Todo(description));
    }

    /**
     * Processes the deadline command by extracting the task description.
     * Checks if the description and the deadline date/time is not empty before creating a new Deadline object.
     *
     * @param userInput The full command string entered by the user (e.g. deadline read book /by Sunday).
     */
    private static void handleDeadlineInput(String userInput) {
        // Check if the input is valid
        int lengthOfStringDeadline = COMMAND_DEADLINE.length();
        if (userInput.trim().length() == lengthOfStringDeadline) {
            MinionResponses.printWithLines(MinionResponses.ERROR_EMPTY_DEADLINE);
            return;
        }

        int byIndex = userInput.indexOf("/by");

        // Check if /by delimiter is missing
        if (byIndex == -1) {
            MinionResponses.printWithLines(MinionResponses.ERROR_MISSING_BY);
            return;
        }

        String description = userInput.substring(lengthOfStringDeadline + 1, byIndex).trim();
        String by = userInput.substring(byIndex + 3).trim();

        // Check if any part is empty
        if (description.isEmpty() || by.isEmpty()) {
            MinionResponses.printWithLines(MinionResponses.ERROR_DEADLINE_PART_EMPTY);
            return;
        }

        addTaskToList(new Deadline(description, by));
    }

    /**
     * Processes the event command by extracting the task description.
     * Checks if the description, and the time range is not empty before creating a new Event object.
     *
     * @param userInput The full command string entered by the user (e.g. event meeting /from 2pm /to 4pm ).
     */
    private static void handleEventInput(String userInput) {
        //Check if the input is valid
        int lengthOfStringEvent = COMMAND_EVENT.length();
        if (userInput.trim().length() == lengthOfStringEvent) {
            MinionResponses.printWithLines(MinionResponses.ERROR_EMPTY_EVENT);
            return;
        }

        int fromIndex = userInput.indexOf("/from");
        int toIndex = userInput.indexOf("/to");

        // Check for missing delimiters (/from or /to)
        if (fromIndex == -1 || toIndex == -1) {
            MinionResponses.printWithLines(MinionResponses.ERROR_MISSING_EVENT_DELIMITERS);
            return;
        }

        // Check if /from comes before /to
        if (fromIndex > toIndex) {
            MinionResponses.printWithLines(MinionResponses.ERROR_EVENT_TIME_ORDER);
            return;
        }

        String description = userInput.substring(lengthOfStringEvent + 1, fromIndex).trim();
        String from = userInput.substring(fromIndex + 5, toIndex).trim();
        String to = userInput.substring(toIndex + 3).trim();

        // Check if any part is empty
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            MinionResponses.printWithLines(MinionResponses.ERROR_EVENT_PART_EMPTY);
            return;
        }

        addTaskToList(new Event(description, from, to));
    }
}