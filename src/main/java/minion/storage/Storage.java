package minion.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
 * Optimized to handle hybrid date/time formats using java.time objects.
 */
public class Storage {

    /** The path to the data file. */
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

        if (!f.exists()) {
            return loadedTasks;
        }

        try {
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String line = s.nextLine();
                if (!line.trim().isEmpty()) {
                    loadedTasks.add(parseTaskFromLine(line));
                }
            }
            s.close();
        } catch (FileNotFoundException e) {
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

        if (f.getParentFile() != null) {
            f.getParentFile().mkdirs();
        }

        FileWriter fw = new FileWriter(filePath);
        for (Task task : tasks) {
            fw.write(task.toFileFormat() + System.lineSeparator());
        }
        fw.close();
    }

    /**
     * Translates a single line of text into a specific Task object.
     * Handles reconstruction of LocalDate, LocalTime, and LocalDateTime objects.
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
            // Indices: 3(byStr), 4(date), 5(time), 6(dateTime)
            task = new Deadline(description, parts[3],
                    parseDate(parts[4]),
                    parseTime(parts[5]),
                    parseDateTime(parts[6]));
            break;
        case "E":
            // Indices: 3(fromStr), 4(toStr), 5-7(from objects), 8-10(to objects)
            task = new Event(description, parts[3], parts[4],
                    parseDate(parts[5]), parseTime(parts[6]), parseDateTime(parts[7]),
                    parseDate(parts[8]), parseTime(parts[9]), parseDateTime(parts[10]));
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

    /**
     * Reconstructs a LocalDate object from a string saved in the file.
     *
     * @param input The string representation of the date, or "null".
     * @return The parsed LocalDate, or null if the input string is "null".
     */
    private LocalDate parseDate(String input) {
        return input.equals("null") ? null : LocalDate.parse(input);
    }

    /**
     * Reconstructs a LocalTime object from a string saved in the file.
     *
     * @param input The string representation of the time, or "null".
     * @return The parsed LocalTime, or null if the input string is "null".
     */
    private LocalTime parseTime(String input) {
        return input.equals("null") ? null : LocalTime.parse(input);
    }

    /**
     * Reconstructs a LocalDateTime object from a string saved in the file.
     *
     * @param input The string representation of the date and time, or "null".
     * @return The parsed LocalDateTime, or null if the input string is "null".
     */
    private LocalDateTime parseDateTime(String input) {
        return input.equals("null") ? null : LocalDateTime.parse(input);
    }
}