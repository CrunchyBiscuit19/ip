import java.util.Scanner;

public class Ui {
    private static Scanner scanner = new Scanner(System.in);

    public static void showWelcomeMessage() {
        System.out.println("____________________________________________________________\r\n" +
                " Hello! I'm Nemo!\r\n" +
                " What can I do for you?\r\n" +
                "____________________________________________________________");
    }

    public static String getNextInput() {
        System.out.print("> ");
        String input = scanner.nextLine();
        return input;
    }

    public static void processInput(String input, Store store, Loader loader) {
        System.out.println("____________________________________________________________");
        String[] splitInput = input.split(" ", 2);
        String commandStr = splitInput[0];
        String args = splitInput.length >= 2 ? splitInput[1] : "";
        try {
            Command command = Command.fromString(commandStr);
            command.operation(args, store, loader);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("____________________________________________________________");
    }

    public static void cleanup() {
        scanner.close();
    }
}
