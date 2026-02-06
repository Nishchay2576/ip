package minion;

import java.util.Scanner;

/**
 * Main class for the chatbot Minion.
 * Provides CLI for users to manage todos, deadlines and events.
 */
public class Minion {

    /** Separator line for console output formatting. */
    private static final String LINE_BREAK = "\t____________________________________________________________";

    /** Welcome message displayed. */
    private static final String WELCOME_MESSAGE = "\t  Hello! I'm Minion!\n\t  What can I do for you?";

    /** Bye message displayed. */
    private static final String BYE_MESSAGE = "\t  Bye. Hope to see you soon!";

    // Command Constants
    /** Command keyword for terminating the application. */
    private static final String COMMAND_BYE = "bye";

    /** Command keyword for displaying the current list of tasks. */
    private static final String COMMAND_LIST = "list";

    /** Command keyword for marking a task as completed. */
    private static final String COMMAND_MARK = "mark";

    /** Command keyword for marking a task as not yet completed. */
    private static final String COMMAND_UNMARK = "unmark";

    /** Command keyword for adding a todo task. */
    private static final String COMMAND_TODO = "todo";

    /** Command keyword for adding a task with a deadline. */
    private static final String COMMAND_DEADLINE = "deadline";

    /** Command keyword for adding a timed event. */
    private static final String COMMAND_EVENT = "event";

    /** The max number of tasks that can be stored. */
    private static final int MAX_TASKS = 100;

    /** Array to store task objects. */
    private static final Task[] tasks = new Task[MAX_TASKS];

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
                System.out.println(LINE_BREAK);
                System.out.println("\t  Please give a valid command.");
                System.out.println(LINE_BREAK);
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
        if (str == null || str.isEmpty()) return true;

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
     * @return -1 if the task number is invalid, and the task number if a valid task number is entered
     */
    private static int getValidTaskNumber(String[] parts) {
        // Check format
        if (parts.length != 2) {
            System.out.println("\t  Please enter the command in the format: [command] [task number]");
            return -1;
        }

        // Check if it's an integer
        if (isNotInteger(parts[1])) {
            System.out.println("\t  Please enter a valid integer for the task number.");
            return -1;
        }

        int taskNumber = Integer.parseInt(parts[1]);
        int totalTasks = Task.getTotalNumberOfTasks();

        // Check bounds
        if (taskNumber <= 0 || taskNumber > totalTasks) {
            System.out.println("\t  Invalid task number. You have " + totalTasks + " tasks.");
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
        System.out.println(LINE_BREAK);

        // Check for Task array capacity
        if (Task.getTotalNumberOfTasks() >= MAX_TASKS) {
            System.out.println("\t  List is full! Cannot add more tasks.");
            System.out.println(LINE_BREAK);
            return;
        }

        // Add task to the task array
        int index = Task.getTotalNumberOfTasks() - 1;
        tasks[index] = task;

        System.out.println("\t  Got it. I've added this task:");
        System.out.println("\t    " + task.toString());
        System.out.println("\t  Now you have " + Task.getTotalNumberOfTasks() + " tasks in the list.");
        System.out.println(LINE_BREAK);
    }

    // Welcome and Bye messages

    /**
     * Prints the welcome message of the chatbot.
     * Used at the start of the program.
     */
    private static void printWelcomeMessage() {
        System.out.println(LINE_BREAK);
        System.out.println(WELCOME_MESSAGE);
        System.out.println(LINE_BREAK);
    }

    /**
     * Prints bye message and terminates the program.
     */
    private static void printByeMessage() {
        System.out.println(LINE_BREAK);
        System.out.println(BYE_MESSAGE);
        System.out.println(LINE_BREAK);
    }

    // Methods handling different user inputs

    /**
     * Displays the current list of tasks to the user.
     * If the list is empty, prints a message indicating that no tasks have been stored yet.
     */
    private static void handleListInput() {
        System.out.println(LINE_BREAK);

        // Handle the empty list case first
        if (Task.getTotalNumberOfTasks() == 0) {
            System.out.println("\t  There are no elements in your list!");
            System.out.println(LINE_BREAK);
            return;
        }

        // Gives all tasks in the task array
        System.out.println("\t  Here are the tasks in your list:");
        for (int i = 1; i <= Task.getTotalNumberOfTasks(); i++) {
            Task currentTask = tasks[i - 1];
            System.out.println("\t  " + i + ". " + currentTask.toString());
        }

        System.out.println(LINE_BREAK);
    }

    /**
     * Processes the mark command to set a specific task as completed.
     * Checks if the task number is valid and also if the task has already been marked.
     *
     * @param userInput The full command string entered by the user (e.g. mark 1).
     */
    private static void handleMarkInput(String userInput) {
        System.out.println(LINE_BREAK);

        // Split input by one or more whitespace characters
        String[] parts = userInput.split("\\s+");

        // check for valid task number
        int taskNumber = getValidTaskNumber(parts);

        if (taskNumber == -1) {
            System.out.println(LINE_BREAK);
            return;
        }

        Task taskToMark = tasks[taskNumber - 1];

        // Check if already marked
        if (taskToMark.getStatusIcon().equals("X")) {
            System.out.println("\t  This task has already been marked as done!");
            System.out.println(LINE_BREAK);
            return;
        }

        System.out.println("\t  Nice, I've marked this task as done:");
        taskToMark.setDone(true);
        System.out.println("\t  " + taskToMark.toString());
        System.out.println(LINE_BREAK);
    }

    /**
     * Processes the unmark command to set a specific task as not completed.
     * Checks if the task number is valid and also if the task has not been marked yet.
     *
     * @param userInput The full command string entered by the user (e.g. unmark 1).
     */
    private static void handleUnmarkInput(String userInput) {
        System.out.println(LINE_BREAK);

        // Split input by one or more whitespace characters
        String[] parts = userInput.split("\\s+");

        // check for valid task number
        int taskNumber = getValidTaskNumber(parts);
        if (taskNumber == -1) {
            System.out.println(LINE_BREAK);
            return;
        }

        Task taskToUnmark = tasks[taskNumber - 1];

        // Check if already marked
        if (taskToUnmark.getStatusIcon().equals(" ")) {
            System.out.println("\t  This task has not been marked yet!");
            System.out.println(LINE_BREAK);
            return;
        }

        System.out.println("\t  OK, I've marked this task as not done yet:");
        taskToUnmark.setDone(false);
        System.out.println("\t  " + taskToUnmark.toString());
        System.out.println(LINE_BREAK);
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
            System.out.println(LINE_BREAK);
            System.out.println("\t  The description of a todo cannot be empty!");
            System.out.println(LINE_BREAK);
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
            System.out.println(LINE_BREAK);
            System.out.println("\t  The description of a deadline cannot be empty!");
            System.out.println(LINE_BREAK);
            return;
        }

        int byIndex = userInput.indexOf("/by");

        // Check if /by delimiter is missing
        if (byIndex == -1) {
            System.out.println(LINE_BREAK);
            System.out.println("\t  Please include a '/by' to specify the deadline!");
            System.out.println(LINE_BREAK);
            return;
        }

        String description = userInput.substring(lengthOfStringDeadline + 1, byIndex).trim();
        String by = userInput.substring(byIndex + 3).trim();

        // Check if any part is empty
        if (by.isEmpty()) {
            System.out.println(LINE_BREAK);
            System.out.println("\t  The deadline date/time cannot be empty!");
            System.out.println(LINE_BREAK);
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
            System.out.println(LINE_BREAK);
            System.out.println("\t  The description of an event cannot be empty!");
            System.out.println(LINE_BREAK);
            return;
        }

        int fromIndex = userInput.indexOf("/from");
        int toIndex = userInput.indexOf("/to");

        // Check for missing delimiters (/from or /to)
        if (fromIndex == -1 || toIndex == -1) {
            System.out.println(LINE_BREAK);
            System.out.println("\t  Please use /from and /to to specify the event duration!");
            System.out.println(LINE_BREAK);
            return;
        }

        // Check if /from comes before /to
        if (fromIndex > toIndex) {
            System.out.println(LINE_BREAK);
            System.out.println("\t  The start time (/from) must come before the end time (/to)!");
            System.out.println(LINE_BREAK);
            return;
        }

        String description = userInput.substring(lengthOfStringEvent + 1, fromIndex).trim();
        String from = userInput.substring(fromIndex + 5, toIndex).trim();
        String to = userInput.substring(toIndex + 3).trim();

        // Check if any part is empty
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            System.out.println(LINE_BREAK);
            System.out.println("\t  Event description, start time, or end time cannot be empty!");
            System.out.println(LINE_BREAK);
            return;
        }

        addTaskToList(new Event(description, from, to));
    }
}