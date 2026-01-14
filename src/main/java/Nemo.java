import java.util.Scanner;

public class Nemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("____________________________________________________________\r\n" + 
                " Hello! I'm Nemo!\r\n" + 
                " What can I do for you?\r\n" +
                "____________________________________________________________");
        
        String command = "";
        while (!command.equals(Command.BYE.toString())) {
            System.out.print("> ");
            command = scanner.nextLine();
            System.out.println("____________________________________________________________");
            if (command.equals(Command.BYE.toString())) {
                System.out.println("Bye. Hope to see you again soon!");
            } else {
                System.out.println(command);
            }
            System.out.println("____________________________________________________________");
        }

        scanner.close();
    }
}