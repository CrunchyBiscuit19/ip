import java.util.Optional;
import java.util.Scanner;
import java.text.MessageFormat;

public class Nemo {
    public static void main(String[] args) {
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

            Optional<Command> processed = Command.fromString(input);
            if (processed.isPresent()) {
                processed.get().operation(store);
            } else {
                store.add(input);
                System.out.println(MessageFormat.format("Added: {0}", input));
            }

            System.out.println("____________________________________________________________");
        }

        scanner.close();
    }
}