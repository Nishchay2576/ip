package minion;

import java.util.Scanner;

public class Minion {
    public static void main(String[] args) {
        Task[] tasks = new Task[100];
        int taskCounter = 0;

        System.out.println("\t____________________________________________________________");
        System.out.println("\tHello! I'm Minion!");
        System.out.println("\tWhat can I do for you?");
        System.out.println("\t____________________________________________________________");

        Scanner in = new Scanner(System.in);

        while (true) {
            String userInput = in.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                break;
            }

            if (userInput.equalsIgnoreCase("list")) {
                System.out.println("\t____________________________________________________________");
                System.out.println("\tHere are the tasks in your list:");
                for (int i = 1; i < taskCounter + 1; i++) {
                    System.out.println("\t" + i + ". " + "[" + tasks[i - 1].getStatusIcon() + "] " + tasks[i - 1].getDescription());
                }
                System.out.println("\t____________________________________________________________");
                continue;
            }

            if (userInput.toLowerCase().startsWith("mark")) {
                System.out.println("\t____________________________________________________________");

                if (userInput.length() < 6) {
                    System.out.println("\tEnter a valid task number.");
                    continue;
                }

                if (!Character.isDigit(userInput.charAt(5))) {
                    System.out.println("\tEnter a valid task number.");
                    continue;
                }

                int taskToMark = Integer.parseInt((userInput.substring(5)));

                if (taskToMark > taskCounter) {
                    System.out.println("\tYou haven't added these many tasks yet. Enter a valid task number.");
                    System.out.println("\t____________________________________________________________");
                    continue;
                } else if (taskToMark <= 0) {
                    System.out.println("\tEnter a valid task number.");
                    continue;
                } else if (tasks[taskToMark - 1].getStatusIcon().equals("X")) {
                    System.out.println("\tThis task has already been marked as done!");
                    continue;
                }

                System.out.println("\tNice, I've marked this task as done:");
                System.out.println("\t" + tasks[taskToMark - 1].getDescription());
                tasks[taskToMark - 1].setDone(true);
                System.out.println("\t____________________________________________________________");
                continue;
            }

            if (userInput.toLowerCase().startsWith("unmark")) {
                System.out.println("\t____________________________________________________________");

                if (userInput.length() < 8) {
                    System.out.println("\tEnter a valid task number.");
                    continue;
                }

                if (!Character.isDigit(userInput.charAt(7))) {
                    System.out.println("\tEnter a valid task number.");
                    continue;
                }

                int taskToUnmark = Integer.parseInt((userInput.substring(7)));

                if (taskToUnmark > taskCounter) {
                    System.out.println("\tYou haven't added these many tasks yet. Enter a valid task number.");
                    System.out.println("\t____________________________________________________________");
                    continue;
                } else if (taskToUnmark <= 0) {
                    System.out.println("\tEnter a valid task number.");
                    continue;
                }  else if (tasks[taskToUnmark - 1].getStatusIcon().equals(" ")) {
                    System.out.println("\tThis task has not been marked yet!");
                    continue;
                }

                System.out.println("\tNice, I've unmarked this task as done:");
                System.out.println("\t" + tasks[taskToUnmark - 1].getDescription());
                tasks[taskToUnmark - 1].setDone(false);
                System.out.println("\t____________________________________________________________");
                continue;
            }

            System.out.println("\t____________________________________________________________");
            System.out.println("\tI've added this task to your list:");
            System.out.println("\t" + userInput);
            System.out.println("\t____________________________________________________________");

            tasks[taskCounter] = new Task(userInput);
            taskCounter++;
        }

        System.out.println("\t____________________________________________________________");
        System.out.println("\tBye. Hope to see you soon!");
        System.out.println("\t____________________________________________________________");
    }
}