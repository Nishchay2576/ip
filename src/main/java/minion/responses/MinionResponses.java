package minion.responses;

public class MinionResponses {
    /** UI Formatting and standard messages */
    public static final String LINE_BREAK = "\t____________________________________________________________";
    public static final String WELCOME_MESSAGE = "\t  Hello! I'm Minion :D\n\t  What can I do for you?";
    public static final String BYE_MESSAGE = "\t  Bye. Hope to see you soon :)";

    /**
     * Prints a message wrapped in horizontal line breaks.
     * @param message The text to be displayed.
     */
    public static void printWithLines(String message) {
        System.out.println(LINE_BREAK);
        System.out.println(message);
        System.out.println(LINE_BREAK);
    }
}
