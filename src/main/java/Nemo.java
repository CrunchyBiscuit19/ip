import java.util.Optional;
import java.util.Scanner;
import java.text.MessageFormat;

public class Nemo {
    public static void main(String[] args1) {
        Scanner scanner = new Scanner(System.in);
        Store store = new Store();
        
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
                    command.get().operation(store, args);
                } else {
                    store.add(input);
                    System.out.println(MessageFormat.format("Added: {0}", input));
                }
            }

            System.out.println("____________________________________________________________");
        }

        scanner.close();
    }
}