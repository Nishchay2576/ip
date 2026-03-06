package minion.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that starts and ends at specific times.
 */
public class Event extends Task {
    /** Represents the starting time of the event as a raw string. */
    private String timeFrom;
    /** Represents the ending time of the event as a raw string. */
    private String timeTo;

    // Start time smart variables
    private LocalDate fromDate;
    private LocalTime fromTime;
    private LocalDateTime fromDateTime;

    // End time smart variables
    private LocalDate toDate;
    private LocalTime toTime;
    private LocalDateTime toDateTime;

    /**
     * Initializes an event task with specific smart date/time objects and fallback strings.
     *
     * @param description Text describing the task.
     * @param timeFrom Raw string for the start time.
     * @param timeTo Raw string for the end time.
     * @param fromDate Start date, or null.
     * @param fromTime Start time, or null.
     * @param fromDateTime Start date and time, or null.
     * @param toDate End date, or null.
     * @param toTime End time, or null.
     * @param toDateTime End date and time, or null.
     */
    public Event(String description, String timeFrom, String timeTo,
                 LocalDate fromDate, LocalTime fromTime, LocalDateTime fromDateTime,
                 LocalDate toDate, LocalTime toTime, LocalDateTime toDateTime) {
        super(description);
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.fromDateTime = fromDateTime;
        this.toDate = toDate;
        this.toTime = toTime;
        this.toDateTime = toDateTime;
    }

    /**
     * Helper to format the display string based on which smart variables are available.
     */
    private String getFormattedTime(String raw, LocalDate d, LocalTime t, LocalDateTime dt) {
        if (dt != null) {
            return dt.format(DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm"));
        } else if (d != null) {
            return d.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        } else if (t != null) {
            return t.format(DateTimeFormatter.ofPattern("HH:mm"));
        }
        return raw;
    }

    /**
     * Returns a string representation of the event task.
     *
     * @return Formatted string for console display.
     */
    @Override
    public String toString() {
        String displayFrom = getFormattedTime(timeFrom, fromDate, fromTime, fromDateTime);
        String displayTo = getFormattedTime(timeTo, toDate, toTime, toDateTime);
        return "[E][" + getStatusIcon() + "] " + description + " (from: " + displayFrom + " to: " + displayTo + ")";
    }

    /**
     * Returns a string representation of the event task to be saved.
     * Stores all components to ensure the state can be perfectly reconstructed.
     *
     * @return Formatted string to be saved.
     */
    @Override
    public String toFileFormat() {
        return String.format("E | %d | %s | %s | %s | %s | %s | %s | %s | %s | %s",
                (isDone ? 1 : 0),
                description,
                timeFrom,
                timeTo,
                (fromDate != null ? fromDate : "null"),
                (fromTime != null ? fromTime : "null"),
                (fromDateTime != null ? fromDateTime : "null"),
                (toDate != null ? toDate : "null"),
                (toTime != null ? toTime : "null"),
                (toDateTime != null ? toDateTime : "null")
        );
    }
}