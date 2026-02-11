package minion.task;

/**
 * Represents a general task in the Minion chatbot.
 */
public abstract class Task {

    /** Description of the task. */
    protected String description;

    /** Completion status of the task*/
    protected boolean isDone;

    /** Global count of all task objects created*/
    protected static int totalNumberOfTasks = 0;

    /**
     * Initializes a task with a description and sets its completion status to false.
     * Increments the global task counter upon creation.
     * @param description Text describing the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        totalNumberOfTasks++;
    }

    /**
     * Returns an icon representing the completion of the task.
     * @return "X" if the task is done, or a space " " if it is not.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns the description of the task.
     * @return The task description string.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the total number of tasks created across all subclasses.
     * @return The current static task count.
     */
    public static int getTotalNumberOfTasks(){
        return totalNumberOfTasks;
    }

    /**
     * Reduces the total number of tasks created across all subclasses by 1.
     */
    public static void reduceTotalNumberOfTasksByOne(){
        totalNumberOfTasks -= 1;
    }

    /**
     * Updates the completion status of the task.
     * @param isDone The new completion status of the task to be set.
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }
}