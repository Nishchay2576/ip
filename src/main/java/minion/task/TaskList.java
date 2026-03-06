package minion.task;

import java.util.ArrayList;

/**
 * Represents a list of tasks in the Minion chatbot.
 * Provides methods to manipulate the task list, such as adding,
 * deleting, and retrieving tasks.
 */
public class TaskList {

    /** The collection of tasks managed by the chatbot. */
    private final ArrayList<Task> tasks;

    /** Initializes an empty TaskList. */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Initializes a TaskList with an existing collection of tasks.
     * Typically used when loading data from storage.
     *
     * @param initialTasks An ArrayList of tasks to populate the list.
     */
    public TaskList(ArrayList<Task> initialTasks) {
        this.tasks = initialTasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task object to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from the list based on its index.
     *
     * @param index The zero-based index of the task to be removed.
     * @return The task that was removed from the list.
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the list based on its index.
     *
     * @param index The zero-based index of the task to retrieve.
     * @return The task at the specified index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the current number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Checks if the task list contains any tasks.
     *
     * @return True if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the underlying ArrayList of tasks.
     * Useful for passing the entire collection to the Storage class for saving.
     *
     * @return The ArrayList containing all tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Searches for tasks that contain the specified keyword in their description.
     * The search is case-insensitive.
     *
     * @param keyword The string to search for.
     * @return A new ArrayList containing only the matching tasks.
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        String searchTarget = keyword.toLowerCase(); // Convert keyword to lower case once

        for (Task task : tasks) {
            // Convert each description to lower case before checking
            if (task.getDescription().toLowerCase().contains(searchTarget)) {
                matches.add(task);
            }
        }
        return matches;
    }
}