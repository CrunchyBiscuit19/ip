package nemo.command;

import nemo.store.Store;
import nemo.store.Loader;
import nemo.task.Task;
import nemo.task.Todo;
import nemo.task.Deadline;
import nemo.task.Event;
import nemo.exception.NotCommandException;
import java.util.HashMap;
import java.text.MessageFormat;

public enum Command {
    // Solution below adapted from https://stackoverflow.com/a/14968372
    BYE("bye") {
        @Override
        public void operation(String args, Store store, Loader loader) {
            shouldExit = true;
            loader.save(store);
            System.out.println("Bye. Hope to see you again soon!");
        }
    },
    LIST("list") {
        @Override
        public void operation(String args, Store store, Loader loader) {
            shouldExit = false;
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
        public void operation(String args, Store store, Loader loader) {
            changeMark(args, store, true);
        }
    },
    UNMARK("unmark") {
        @Override
        public void operation(String args, Store store, Loader loader) {
            changeMark(args, store, false);
        }
    },
    TODO("todo") {
        @Override
        public void operation(String args, Store store, Loader loader) {
            shouldExit = false;
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
        public void operation(String args, Store store, Loader loader) {
            shouldExit = false;
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
        public void operation(String args, Store store, Loader loader) {
            shouldExit = false;
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
        public void operation(String args, Store store, Loader loader) {
            shouldExit = false;
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
    private static HashMap<String, Command> commandMap;
    private static boolean shouldExit;

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

    public static Command fromString(String input) throws NotCommandException {
        if (commandMap.containsKey(input.toLowerCase())) {
            return commandMap.get(input);
        }
        throw new NotCommandException("Not valid command.");
    }

    abstract public void operation(String args, Store store, Loader loader);

    @Override
    public String toString() {
        return this.command;
    }

    private static void changeMark(String args, Store store, boolean mark) {
        shouldExit = false;
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

    public static boolean isExit() {
        return shouldExit;
    }
}
