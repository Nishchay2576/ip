package minion.responses;

public class MinionResponses {
    /** UI Formatting. */
    public static final String LINE_BREAK = "\t______________________________________________________________________";

    /** General Bot Messages. */
    public static final String WELCOME_MESSAGE = "\t  Bello! I'm Minion :D\n" +
            "\t  Ready to conquer NUS today boss!\n" +
            "\t  What's the plan for today?";
    public static final String BYE_MESSAGE = "\t  Byee. Hope you have an amazing day ahead :)";
    public static final String UNKNOWN_COMMAND = "\t  Bi-do... I don't think that's a real command... Try again?";

    /** Task List Messages. */
    public static final String MESSAGE_EMPTY_LIST = "\t  Your list is as empty as a banana peel! Try adding something";
    public static final String MESSAGE_LIST_HEADER = "\t  Here’s the breakdown of your mission:\n";
    public static final String MESSAGE_ADD_SUCCESS = "\t  Ba-na-na! I've tossed this onto the pile for you:\n";
    public static final String MESSAGE_MARK_SUCCESS = "\t  Woohoo! I've marked:\n";
    public static final String MESSAGE_UNMARK_SUCCESS = "\t  OK, I've marked this task as not done yet:\n";
    public static final String MESSAGE_DELETE_SUCCESS = "\t  Noted.This task is gone, " +
            "just like my sleep during finals week:\n";

    /** Error Messages. */
    public static final String ERROR_INVALID_FORMAT = "\t  Syntax Error! Use: [command] [task number].\n" +
            "\t  Check your 'mark', 'unmark' or 'delete' command parameters!";
    public static final String ERROR_NOT_INT = "\t  Please enter a valid integer for the task number.";
    public static final String ERROR_ALREADY_DONE = "\t  This task is already marked. Go enjoy a snack!";
    public static final String ERROR_NOT_DONE_YET = "\t  This task isn't done yet. " +
            "Don't procrastinate like a freshman!";
    public static final String ERROR_EMPTY_TODO = "\t  The description of a todo cannot be empty!";
    public static final String ERROR_EMPTY_DEADLINE = "\t  The description of a deadline cannot be empty!";
    public static final String ERROR_MISSING_BY = "\t  Please include a '/by' to specify the deadline!";
    public static final String ERROR_DEADLINE_PART_EMPTY = "\t  The deadline description or date/time " +
            "cannot be empty!";
    public static final String ERROR_EMPTY_EVENT = "\t  The description of an event cannot be empty!";
    public static final String ERROR_MISSING_EVENT_DELIMITERS = "\t  Please use /from and /to to specify " +
            "the event duration!";
    public static final String ERROR_EVENT_TIME_ORDER = "\t  The start time (/from) must come before " +
            "the end time (/to)!";
    public static final String ERROR_EVENT_PART_EMPTY = "\t  One of the event details is empty. " +
            "Check your input format!";
    public static final String ERROR_LOADING_FILE = "\t  Bido... I couldn't find your save file!";

    /**
     * Returns the formatted string for the total task count.
     *
     * @param size The current number of tasks.
     * @return Formatted string with singular/plural handling.
     */
    public static String getTaskCountMessage(int size) {
        String taskWord = (size == 1) ? "task" : "tasks";
        return "\t  Now you have " + size + " " + taskWord + " in the list.";
    }

    /**
     * Prints a message wrapped in horizontal line breaks.
     *
     * @param message The text to be displayed.
     */
    public static void printWithLines(String message) {
        System.out.println(LINE_BREAK);
        System.out.println(message);
        System.out.println(LINE_BREAK);
    }
}