import java.util.Scanner;

public class Minion {
    public static void main(String[] args) {
        String[] tasks = new String[100];
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
                for (int i = 1; i < taskCounter + 1; i++) {
                    System.out.println("\t" + i + ". " + tasks[i - 1]);
                }
                System.out.println("\t____________________________________________________________");
                continue;
            }

            System.out.println("\t____________________________________________________________");
            System.out.println("\tadded: " + userInput);
            System.out.println("\t____________________________________________________________");

            tasks[taskCounter] = userInput;
            taskCounter++;
        }

        System.out.println("\t____________________________________________________________");
        System.out.println("\tBye. Hope to see you soon!");
        System.out.println("\t____________________________________________________________");
    }
}
