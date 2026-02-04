package minion;

/**
 * Represents a task that starts and ends at specific times.
 */
public class Event extends Task{
    private String timeFrom;
    private String timeTo;

    /**
     * Initializes an event task with the specified description, the starting (from) and the ending (to) timings.
     *
     * @param description Text describing the task.
     * @param timeFrom Text describing the starting time of the event.
     * @param timeTo Text describing the ending time of the event.
     */
    public Event(String description, String timeFrom, String timeTo){
        super(description);
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    /**
     * Returns a string representation of the event task.
     *
     * @return Formatted string for console display.
     */
    @Override
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + description + " (from: " + timeFrom + " to: " + timeTo + ")";
    }
}
