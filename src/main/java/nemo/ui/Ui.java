package nemo.ui;

import java.util.Scanner;

import nemo.command.Command;
import nemo.store.Loader;
import nemo.store.Store;

/**
 * Responsible for displaying messages, reading input, and routing input to the
 * command processor.
 */
public class Ui {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Print the application welcome banner to standard output.
     */
    public static void showWelcomeMessage() {
        System.out.println("____________________________________________________________\r\n"
                + " Hello! I'm Nemo!\r\n"
                + " What can I do for you?\r\n"
                + "____________________________________________________________");
    }

    /**
     * Prompt the user and return the next line of console input.
     *
     * @return the raw user input line
     */
    public static String getNextInput() {
        System.out.print("> ");
        String input = scanner.nextLine();
        return input;
    }

    /**
     * Parse command and arguments, execute command, then print
     * surrounding separators and error messages.
     *
     * @param input  the raw input line from the user
     * @param store  the task store to operate on
     * @param loader the store loader
     */
    public static void processInput(String input, Store store, Loader loader) {
        System.out.println("____________________________________________________________");
        String[] splitInput = input.split(" ", 2);
        String commandStr = splitInput[0];
        String args = splitInput.length >= 2 ? splitInput[1] : "";
        try {
            Command.fromString(commandStr).operate(args, store, loader);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("____________________________________________________________");
    }

    /**
     * Close scanner and any future required cleanup tasks.
     */
    public static void cleanup() {
        scanner.close();
    }
}
