package minion.task;
/**
 * Represents a task without any specified time constraints.
 */
public class Todo extends Task{

    /**
     * Initializes a Todo task with the specified description.
     *
     * @param description Text describing the task.
     */
    public Todo(String description){
        super(description);
    }

    /**
     * Returns a string representation of the todo task.
     *
     * @return Formatted string for console display.
     */
    @Override
    public String toString() {
        return "[T][" + getStatusIcon() + "] " + description;
    }
}
