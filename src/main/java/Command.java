import java.util.HashMap;
import java.text.ParseException;

public enum Command {
    // Solution below adapted from https://stackoverflow.com/a/14968372
    BYE("bye") {
        @Override
        public void operation(String args) {
            System.out.println("Bye. Hope to see you again soon!");
        }
    },
    LIST("list") {
        @Override
        public void operation(String args) {
            if (store.size() == 0) {
                System.out.println("Nothing in the list yet.");
                return;
            }
            System.out.println("Here are the tasks in your list:");
            System.out.println(store.generateList());
        }
    },
    MARK("mark") {
        @Override
        public void operation(String args) {
            changeMark(true, args);
        }
    },
    UNMARK("unmark") {
        @Override
        public void operation(String args) {
            changeMark(false, args);
        }
    },
    TODO("todo") {
        @Override
        public void operation(String args) {
            try {
                HashMap<String, String> argMap = Task.parseArgs(args);
                Todo todo = new Todo(argMap);
                store.addAndReport(todo);
            } catch (ParseException e) {
                System.err.println(e.getMessage());
                return;
            }
        }
    },
    DEADLINE("deadline") {
        @Override
        public void operation(String args) {
            try {
                HashMap<String, String> argMap = Task.parseArgs(args);
                Deadline deadline = new Deadline(argMap);
                store.addAndReport(deadline);
            } catch (ParseException e) { 
                System.err.println(e.getMessage());
                return;
            }
        }
    },
    EVENT("event") {
        @Override
        public void operation(String args) {
            try {
                HashMap<String, String> argMap = Task.parseArgs(args);
                Event event = new Event(argMap);
                store.addAndReport(event);
            } catch (ParseException e) {
                System.err.println(e.getMessage());
                return;
            }
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

    public static Command fromString(String input) throws NotCommandException {
        if (commandMap.containsKey(input.toLowerCase())) {
            return commandMap.get(input);
        }
        throw new NotCommandException("Not valid command.");
    }

    abstract void operation(String args);

    @Override
    public String toString() {
        return this.command;
    }

    private static Task parseTaskId(Store store, String toParse) throws NumberFormatException, IndexOutOfBoundsException {
        try {
            int id = Integer.parseInt(toParse) - 1;
            return store.get(id);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Not task ID.");
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Invalid task ID.");
        }
    }

    private static void changeMark(boolean mark, String args) {
        try {
            Task task = parseTaskId(store, args.split(" ")[0]);
            if (mark) {
                task.mark();
                System.out.println("Nice! I've marked this task as done:");
            } else {
                task.unmark();
                System.out.println("OK, I've marked this task as not done yet:");
            }
            System.out.println(task.getSummary());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
