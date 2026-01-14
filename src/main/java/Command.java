import java.util.HashMap;
import java.util.Optional;
import java.text.MessageFormat;

public enum Command {
    // Solution below adapted from https://stackoverflow.com/a/14968372
    BYE("bye") {
        @Override
        public void operation(Store store, String args) {
            System.out.println("Bye. Hope to see you again soon!");
        }
    },
    LIST("list") {
        @Override
        public void operation(Store store, String args) {
            System.out.println("Here are the tasks in your list:");
            System.out.println(store.generateList());
        }
    },
    MARK("mark") {
        @Override
        public void operation(Store store, String args) {
            Optional<Task> potentialTask = parseTaskId(store, args.split(" ")[0]);
            if (!potentialTask.isPresent()) return;
            Task task = potentialTask.get();
            task.mark();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(MessageFormat.format("[{0}] {1}", task.getStatusIcon(), task.description));
        }
    },
    UNMARK("unmark") {
        @Override
        public void operation(Store store, String args) {
            Optional<Task> potentialTask = parseTaskId(store, args.split(" ")[0]);
            if (!potentialTask.isPresent()) return;
            Task task = potentialTask.get();
            task.unmark();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println(MessageFormat.format("[{0}] {1}", task.getStatusIcon(), task.description));
        }
    };

    private final String command;
    private static Store store;
    private static HashMap<String, Command> commandMap;

    Command(String command) {
        this.command = command;
    }

    // Solution below adapted from https://stackoverflow.com/a/25411863
    static {
        commandMap = new HashMap<>();
        for (Command cmd : Command.values()) {
            commandMap.put(cmd.command.toLowerCase(), cmd);
        }
    }

    public static void assignStore(Store store) {
        Command.store = store;
    }

    public static Optional<Command> fromString(String input) {
        if (commandMap.containsKey(input.toLowerCase())) {
            return Optional.of(commandMap.get(input));
        }
        return Optional.empty();
    }

    abstract void operation(Store store, String args);

    Optional<Task> parseTaskId(Store store, String toParse) {
        int id = 0;
        try {
            id = Integer.parseInt(toParse) - 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Incorrect arguments, try again.");
            return Optional.empty();
        } catch (NumberFormatException e) {
            System.out.println("Not item ID, try again.");
            return Optional.empty();
        }
        if (id >= store.size() || id < 0) {
            System.out.println("Invalid item ID, try again.");
            return Optional.empty();
        }
        return Optional.of(store.get(id));
    }

    @Override
    public String toString() {
        return this.command;
    }
}
