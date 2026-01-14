import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.text.MessageFormat;

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
            Optional<Task> potentialTask = parseTaskId(store, args.split(" ")[0]);
            if (!potentialTask.isPresent()) return;
            Task task = potentialTask.get();
            task.mark();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(task.getSummary());
        }
    },
    UNMARK("unmark") {
        @Override
        public void operation(String args) {
            Optional<Task> potentialTask = parseTaskId(store, args.split(" ")[0]);
            if (!potentialTask.isPresent()) return;
            Task task = potentialTask.get();
            task.unmark();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println(task.getSummary());
        }
    },
    Todo("todo") {
        @Override
        public void operation(String args) {
            if (args.isBlank()) {
                System.err.println("Todo goal cannot be empty");
                return;
            }

            Todo todo = new Todo(args);
            store.add(todo);

            System.out.println("Got it. I've added this task:");
            System.out.println(todo.getSummary());
            System.out.println(MessageFormat.format("Now you have {0} tasks in the list.", store.size()));
        }
    },
    DEADLINE("deadline") {
        @Override
        public void operation(String args) {
            Map<String, String> argMap = new HashMap<>();
            String[] argTokens = args.split(" ");
            String lastKey = null;
            StringBuilder goalStringBuilder = new StringBuilder();
            for (String token : argTokens) {
                if (token.startsWith("/")) {
                    lastKey = token.substring(1);
                    argMap.put(lastKey, null);
                } else if (lastKey != null) {
                    argMap.put(lastKey, token);
                } else {
                    goalStringBuilder.append(token);
                    goalStringBuilder.append(' ');
                }
            }
            String goal = goalStringBuilder.toString().trim();

            if (argMap.get("by") == null) {
                System.err.println("Deadline must have a by date.");
                return;
            }

            if (goal.isBlank()) {
                System.err.println("Deadline must have a goal.");
                return;
            }
            
            Deadline deadline = new Deadline(goal, argMap.get("by"));
            store.add(deadline);

            System.out.println("Got it. I've added this task:");
            System.out.println(deadline.getSummary());
            System.out.println(MessageFormat.format("Now you have {0} tasks in the list.", store.size()));
        }
    },
    EVENT("event") {
        @Override
        public void operation(String args) {
            Map<String, String> argMap = new HashMap<>();
            String[] argTokens = args.split(" ");
            String lastKey = null;
            StringBuilder goalStringBuilder = new StringBuilder();
            for (String token : argTokens) {
                if (token.startsWith("/")) {
                    lastKey = token.substring(1);
                    argMap.put(lastKey, null);
                } else if (lastKey != null) {
                    argMap.put(lastKey, token);
                } else {
                    goalStringBuilder.append(token);
                    goalStringBuilder.append(' ');
                }
            }
            String goal = goalStringBuilder.toString().trim();

            if (argMap.get("from") == null || argMap.get("to") == null ) {
                System.err.println("Event must have a from date.");
                return;
            }

            if (goal.isBlank()) {
                System.err.println("Event must have a goal.");
                return;
            }

            Event event = new Event(goal, argMap.get("from"), argMap.get("to"));
            store.add(event);

            System.out.println("Got it. I've added this task:");
            System.out.println(event.getSummary());
            System.out.println(MessageFormat.format("Now you have {0} tasks in the list.", store.size()));
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

    abstract void operation(String args);

    private static Optional<Task> parseTaskId(Store store, String toParse) {
        int id = 0;
        try {
            id = Integer.parseInt(toParse) - 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Incorrect arguments, try again.");
            return Optional.empty();
        } catch (NumberFormatException e) {
            System.err.println("Not item ID, try again.");
            return Optional.empty();
        }
        if (id >= store.size() || id < 0) {
            System.err.println("Invalid item ID, try again.");
            return Optional.empty();
        }
        return Optional.of(store.get(id));
    }

    @Override
    public String toString() {
        return this.command;
    }
}
