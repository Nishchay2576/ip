package minion.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task to be completed by a specified date or time.
 */
public class Deadline extends Task {

    /** The raw text describing the deadline. */
    private String by;
    /** The deadline as a specific date. */
    private LocalDate byDate;
    /** The deadline as a specific time. */
    private LocalTime byTime;
    /** The deadline as a specific date and time. */
    private LocalDateTime byDateTime;

    /**
     * Initializes a deadline task with the specified description and the deadline date/time.
     *
     * @param description Text describing the task.
     * @param by Text describing the deadline time.
     * @param byDate LocalDate representation of the deadline, or null if not applicable.
     * @param byTime LocalTime representation of the deadline, or null if not applicable.
     * @param byDateTime LocalDateTime representation of the deadline, or null if not applicable.
     */
    public Deadline(String description, String by, LocalDate byDate, LocalTime byTime, LocalDateTime byDateTime) {
        super(description);
        this.by = by;
        this.byDate = byDate;
        this.byTime = byTime;
        this.byDateTime = byDateTime;
    }

    /**
     * Returns a string representation of the deadline task.
     *
     * @return Formatted string for console display.
     */
    @Override
    public String toString() {
        String displayBy = by;

        if (byDateTime != null) {
            displayBy = byDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm"));
        } else if (byDate != null) {
            displayBy = byDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        } else if (byTime != null) {
            displayBy = byTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        }

        return "[D][" + getStatusIcon() + "] " + description + " (by: " + displayBy + ")";
    }

    /**
     * Returns a string representation of the deadline task to be saved.
     * Using ISO-8601 strings for date/time objects to ensure reliable re-parsing.
     *
     * @return Formatted string to be saved.
     */
    @Override
    public String toFileFormat() {
        String datePart = (byDate != null) ? byDate.toString() : "null";
        String timePart = (byTime != null) ? byTime.toString() : "null";
        String dateTimePart = (byDateTime != null) ? byDateTime.toString() : "null";

        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by + " | "
                + datePart + " | " + timePart + " | " + dateTimePart;
    }
}