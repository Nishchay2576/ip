package minion.task;

/**
 * Represents a general task in the Minion chatbot.
 */
public abstract class Task {

    /** Represents the description of the task. */
    protected String description;

    /** Represents the completion status of the task. */
    protected boolean isDone;

    /**
     * Initializes a task with a description and sets its completion status to false.
     *
     * @param description Text describing the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns an icon representing the completion of the task.
     *
     * @return "X" if the task is done, or a space " " if it is not.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description string.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Updates the completion status of the task.
     *
     * @param isDone The new completion status of the task to be set.
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }
}