package minion.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import minion.exception.MinionException;
import minion.task.Deadline;
import minion.task.Event;
import minion.task.Task;
import minion.task.Todo;
import minion.responses.MinionResponses;

/**
 * Handles the loading and saving of task data to a local text file.
 * Uses the Scanner and FileWriter classes as specified in class notes.
 */
public class Storage {
    private String filePath;

    /**
     * Initializes the Storage object with a specific file path.
     *
     * @param filePath The path to the data file (e.g., "data/minion.txt").
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the hard drive.
     * If the file does not exist, it returns an empty list.
     *
     * @return An ArrayList containing tasks reconstructed from the file.
     * @throws MinionException If the file is corrupted or unreadable.
     */
    public ArrayList<Task> load() throws MinionException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        File f = new File(filePath);

        // Check if file exists before attempting to read
        if (!f.exists()) {
            return loadedTasks;
        }

        try {
            // Scanner uses the File object as the source of data
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String line = s.nextLine();
                loadedTasks.add(parseTaskFromLine(line));
            }
            s.close();
        } catch (FileNotFoundException e) {
            // Re-throw as MinionException to let the main class handle UI feedback
            throw new MinionException(MinionResponses.ERROR_LOADING_FILE);
        }
        return loadedTasks;
    }

    /**
     * Overwrites the current data file with the updated list of tasks.
     *
     * @param tasks The current list of tasks to be saved.
     * @throws IOException If the writing operation fails.
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        File f = new File(filePath);

        // Ensure the directory exists before writing the file
        if (f.getParentFile() != null) {
            f.getParentFile().mkdirs();
        }

        // FileWriter overwrites the file by default
        FileWriter fw = new FileWriter(filePath);
        for (Task task : tasks) {
            // System.lineSeparator() ensures compatibility across Windows/Mac
            fw.write(task.toFileFormat() + System.lineSeparator());
        }
        // close() is required for the writing operation to be completed
        fw.close();
    }

    /**
     * Private helper to translate a single line of text into a Task object.
     *
     * @param line A pipe-separated string from the text file.
     * @return A specific Task subclass (Todo, Deadline, or Event).
     */
    private Task parseTaskFromLine(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            task = new Deadline(description, parts[3]);
            break;
        case "E":
            task = new Event(description, parts[3], parts[4]);
            break;
        default:
            task = new Todo("Unknown Task Type");
            break;
        }

        if (isDone) {
            task.setDone(true);
        }
        return task;
    }
}