import java.util.Optional;
import java.util.Scanner;

public class Nemo {
    public static void main(String[] args1) {
        Scanner scanner = new Scanner(System.in);
        Store store = new Store();
        Command.assignStore(store);
        
        System.out.println("____________________________________________________________\r\n" + 
                " Hello! I'm Nemo!\r\n" + 
                " What can I do for you?\r\n" +
                "____________________________________________________________");
        
        String input = "";
        while (!input.equals(Command.BYE.toString())) {
            System.out.print("> ");
            input = scanner.nextLine();
            System.out.println("____________________________________________________________");

            if (!input.isEmpty()) {
                String[] splitInput = input.split(" ", 2);
                String potentialCommand = splitInput[0];
                String args = splitInput.length >= 2 ? splitInput[1] : "";
                Optional<Command> command = Command.fromString(potentialCommand);
                if (command.isPresent()) {
                    command.get().operation(args);
                } else {
                    System.err.println("Not a command.");
                }
            }

            System.out.println("____________________________________________________________");
        }

        scanner.close();
    }
}