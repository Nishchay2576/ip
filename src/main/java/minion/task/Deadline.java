package minion.task;

/**
 * Represents a task to be completed by a specified date or time.
 */
public class Deadline extends Task{
    /** Represents the date or time by which the task must be completed. */
    private String by;

    /**
     * Initializes a deadline task with the specified description and the deadline date/time.
     *
     * @param description Text describing the task.
     * @param by Text describing the deadline time.
     */
    public Deadline(String description, String by){
        super(description);
        this.by = by;
    }

    /**
     * Returns a string representation of the deadline task.
     *
     * @return Formatted string for console display.
     */
    @Override
    public String toString() {
        return "[D][" + getStatusIcon() + "] " + description + " (by: " + by + ")";
    }
}