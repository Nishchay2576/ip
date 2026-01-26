import java.util.Scanner;
public class Minion {
    public static void main(String[] args) {
        System.out.println("\t____________________________________________________________");
        System.out.println("\tHello! I'm Minion!");
        System.out.println("\tWhat can I do for you?");
        System.out.println("\t____________________________________________________________");
        String userInput = "";
        Scanner in = new Scanner(System.in);
        while (true) {
            userInput = in.nextLine();
            if (userInput.equalsIgnoreCase("bye")) {
                break;
            }
            System.out.println("\t____________________________________________________________");
            System.out.println("\t" + userInput);
            System.out.println("\t____________________________________________________________");
        }
        System.out.println("\t____________________________________________________________");
        System.out.println("\tBye. Hope to see you soon!");
        System.out.println("\t____________________________________________________________");
    }
}
