import java.nio.file.Path;
import java.util.Scanner;

public class Nemo {
    private Store store;
    private Loader loader;

    public Nemo(Path saveFilePath) {
        store = new Store();
        loader = new Loader(saveFilePath);
        loader.load(store);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

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
                command.operation(args, store, loader);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println("____________________________________________________________");
        }

        scanner.close();
    }

    public static void main(String[] args1) {
        new Nemo(Path.of(System.getProperty("user.home"), "NEMO", "data.txt")).run();
    }
}