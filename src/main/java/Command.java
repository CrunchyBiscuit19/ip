import java.util.HashMap;
import java.text.MessageFormat;

public enum Command {
    // Solution below adapted from https://stackoverflow.com/a/14968372
    BYE("bye") {
        @Override
        public void operation(String args) {
            loader.save();
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
                store.add(todo);
                System.out.println("Got it. I've added this task:");
                System.out.println(todo.getSummary());
                System.out.println(MessageFormat.format("Now you have {0} tasks in the list.", store.size()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
                store.add(deadline);
                System.out.println("Got it. I've added this task:");
                System.out.println(deadline.getSummary());
                System.out.println(MessageFormat.format("Now you have {0} tasks in the list.", store.size()));
            } catch (Exception e) { 
                System.out.println(e.getMessage());
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
                store.add(event);
                System.out.println("Got it. I've added this task:");
                System.out.println(event.getSummary());
                System.out.println(MessageFormat.format("Now you have {0} tasks in the list.", store.size()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    },
    DELETE("delete") {
        @Override
        public void operation(String args) {
            try {
                Task task = store.get(Integer.parseInt(args) - 1);
                String taskSummary = task.getSummary();
                store.remove(Integer.parseInt(args) - 1);
                System.out.println("Noted. I've removed this task:");
                System.out.println(taskSummary);
                System.out.println(MessageFormat.format("Now you have {0} tasks in the list.", store.size()));
            } catch (NumberFormatException e) {
                System.out.println("Not task ID.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid task ID.");
            }
        }
    };

    private final String command;
    private static Store store;
    private static Loader loader;
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

    public static void assignLoader(Loader loader) {
        Command.loader = loader;
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

    private static void changeMark(boolean mark, String args) {
        try {
            Task task = store.get(Integer.parseInt(args) - 1);
            if (mark) {
                task.mark();
                System.out.println("Nice! I've marked this task as done:");
            } else {
                task.unmark();
                System.out.println("OK, I've marked this task as not done yet:");
            }
            System.out.println(task.getSummary());
        } catch (NumberFormatException e) {
            System.out.println("Not task ID.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid task ID.");
        }
    }
}
