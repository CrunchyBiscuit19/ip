import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Command {
    //Solution below adapted from https://stackoverflow.com/a/14968372
    BYE("bye") {
        @Override
        public void operation(Store store) {
            System.out.println("Bye. Hope to see you again soon!");
        }
    },
    LIST("list") {
        @Override
        public void operation(Store store) {
            //System.out.println(store.generateList());
        }
    };

    private final String command;
    private static Store store;
    private static HashMap<String, Command> commandMap;

    Command(String command) {
        this.command = command;
    }

    //Solution below adapted from https://stackoverflow.com/a/25411863
    static {
        commandMap = new HashMap<>();
        for (Command cmd : Command.values()) {
            commandMap.put(cmd.command.toLowerCase(), cmd);
        }
    }
    
    public static void assignStore(Store store) {
        Command.store = store;
    }

    abstract void operation(Store store);

    public static Optional<Command> fromString(String input) {
        if (commandMap.containsKey(input.toLowerCase())) {
            return Optional.of(commandMap.get(input));
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return this.command;
    }
}
