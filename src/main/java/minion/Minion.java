package minion;

import java.util.Scanner;

public class Minion {
    private static final String LINE_BREAK = "\t____________________________________________________________";
    private static final String WELCOME_MESSAGE = "\tHello! I'm Minion!\n\tWhat can I do for you?";
    private static final int MAX_TASKS = 100;
    private static final Task[] tasks = new Task[MAX_TASKS];

    public static void main(String[] args) {
        printWelcomeMessage();
        Scanner in = new Scanner(System.in);

        while (true) {
            String userInput = in.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                printByeMessage();
                break;
            }

            if (userInput.equalsIgnoreCase("list")) {
                handleListInput();
                continue;
            }

            if (userInput.toLowerCase().startsWith("mark")) {
                handleMarkInput(userInput);
                continue;
            }

            if (userInput.toLowerCase().startsWith("unmark")) {
                handleUnmarkInput(userInput);
                continue;
            }

            System.out.println(LINE_BREAK);
            System.out.println("\tI've added this task to your list:");
            System.out.println("\t" + userInput);
            System.out.println(LINE_BREAK);

            Task newTask = new Task(userInput);
            int index = Task.getTotalNumberOfTasks() - 1;
            tasks[index] = newTask;
        }

    }

    public static boolean isNotInteger(String str) {
        if (str == null || str.isEmpty()) return true;
        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private static int getValidTaskNumber(String[] parts) {
        // Check format
        if (parts.length != 2) {
            System.out.println("\tPlease enter the command in the format: [command] [task number]");
            return -1;
        }

        // Check if it's an integer
        if (isNotInteger(parts[1])) {
            System.out.println("\tPlease enter a valid integer for the task number.");
            return -1;
        }

        int taskNumber = Integer.parseInt(parts[1]);
        int totalTasks = Task.getTotalNumberOfTasks();

        // Check bounds
        if (taskNumber <= 0 || taskNumber > totalTasks) {
            System.out.println("\tInvalid task number. You have " + totalTasks + " tasks.");
            return -1;
        }

        return taskNumber;
    }

    private static void printWelcomeMessage() {
        System.out.println(LINE_BREAK);
        System.out.println(WELCOME_MESSAGE);
        System.out.println(LINE_BREAK);
    }

    private static void printByeMessage() {
        System.out.println(LINE_BREAK);
        System.out.println("\tBye. Hope to see you soon!");
        System.out.println(LINE_BREAK);
    }

    private static void handleListInput() {
        System.out.println(LINE_BREAK);

        // Handle the empty list case first
        if (Task.getTotalNumberOfTasks() == 0) {
            System.out.println("\tThere are no elements in your list!");
            System.out.println(LINE_BREAK);
            return;
        }

        //Handle the case where list is full already
        if (Task.getTotalNumberOfTasks() >= MAX_TASKS) {
            System.out.println("\tList is full!");
            return;
        }

        // This only runs if the list is NOT empty
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 1; i <= Task.getTotalNumberOfTasks(); i++) {
            Task currentTask = tasks[i - 1];
            System.out.println("\t" + i + ". [" + currentTask.getStatusIcon() + "] "
                    + currentTask.getDescription());
        }

        System.out.println(LINE_BREAK);
    }

    private static void handleMarkInput(String userInput) {
        System.out.println(LINE_BREAK);

        // Split input by one or more whitespace characters
        String[] parts = userInput.split("\\s+");

        // returns -1 if the task number is invalid, and the correct task number if the task number is valid
        int taskNumber = getValidTaskNumber(parts);

        if (taskNumber == -1) {
            System.out.println(LINE_BREAK);
            return;
        }

        Task taskToMark = tasks[taskNumber - 1];

        // Check if already marked
        if (taskToMark.getStatusIcon().equals("X")) {
            System.out.println("\tThis task has already been marked as done!");
            System.out.println(LINE_BREAK);
            return;
        }

        System.out.println("\tNice, I've marked this task as done:");
        System.out.println("\t" + taskToMark.getDescription());
        taskToMark.setDone(true);
        System.out.println(LINE_BREAK);
    }

    private static void handleUnmarkInput(String userInput) {
        System.out.println(LINE_BREAK);

        // Split input by one or more whitespace characters
        String[] parts = userInput.split("\\s+");

        // returns -1 if the task number is invalid, and the correct task number if the task number is valid
        int taskNumber = getValidTaskNumber(parts);

        if (taskNumber == -1) {
            System.out.println(LINE_BREAK);
            return;
        }

        Task taskToUnmark = tasks[taskNumber - 1];

        // Check if already marked
        if (taskToUnmark.getStatusIcon().equals(" ")) {
            System.out.println("\tThis task has not been marked yet!");
            System.out.println(LINE_BREAK);
            return;
        }

        System.out.println("\tNice, I've marked this task as not done:");
        System.out.println("\t" + taskToUnmark.getDescription());
        taskToUnmark.setDone(false);
        System.out.println(LINE_BREAK);
    }
}