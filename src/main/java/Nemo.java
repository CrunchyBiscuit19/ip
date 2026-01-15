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

            String[] splitInput = input.split(" ", 2);
            String commandStr = splitInput[0];
            String args = splitInput.length >= 2 ? splitInput[1] : "";
            try {
                Command command = Command.fromString(commandStr);
                command.operation(args);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            System.out.println("____________________________________________________________");
        }

        scanner.close();
    }
}