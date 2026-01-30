package nemo.command;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import nemo.exception.NotCommandException;
import nemo.store.Loader;
import nemo.store.Store;
import nemo.task.Deadline;
import nemo.task.Event;
import nemo.task.Task;
import nemo.task.Todo;

/**
 * Enum representing all supported user commands and their associated operations
 * Each enum constant implements the operation method to perform its behavior.
 */
public enum Command {
    // Solution below adapted from https://stackoverflow.com/a/14968372
    BYE("bye") {
        /**
         * Leaves the conversation.
         *
         * @param args   command arguments (later parsed inside command)
         * @param store  the task store to operate on
         * @param loader the loader to save / load the store
         */
        @Override
        public String operation(String args, Store store, Loader loader) throws Exception {
            shouldExit = true;
            loader.save(store);
            return "Bye. Hope to see you again soon!";
        }
    },
    LIST("list") {
        /**
         * Lists all the tasks in the store.
         *
         * @param args   command arguments (later parsed inside command)
         * @param store  the task store to operate on
         * @param loader the loader to save / load the store
         */
        @Override
        public String operation(String args, Store store, Loader loader) throws Exception {
            shouldExit = false;
            if (store.size() == 0) {
                return "Nothing in the list yet.";
            }
            return MessageFormat.format("Here are the tasks in your list:\n{0}", store.generateList());
        }
    },
    MARK("mark") {
        /**
         * Mark a specified task as done.
         *
         * @param args   command arguments (later parsed inside command)
         * @param store  the task store to operate on
         * @param loader the loader to save / load the store
         */
        @Override
        public String operation(String args, Store store, Loader loader) throws Exception {
            return changeMark(args, store, true);
        }
    },
    UNMARK("unmark") {
        /**
         * Mark a specified task as not done.
         *
         * @param args   command arguments (later parsed inside command)
         * @param store  the task store to operate on
         * @param loader the loader to save / load the store
         */
        @Override
        public String operation(String args, Store store, Loader loader) throws Exception {
            return changeMark(args, store, false);
        }
    },
    TODO("todo") {
        /**
         * Add a Todo task. Expected arguments are a goal.
         *
         * @param args   command arguments (later parsed inside command)
         * @param store  the task store to operate on
         * @param loader the loader to save / load the store
         */
        @Override
        public String operation(String args, Store store, Loader loader) throws Exception {
            shouldExit = false;
            HashMap<String, String> argMap = Task.parseArgs(args);
            Todo todo = new Todo(argMap);
            store.add(todo);
            return MessageFormat.format("Got it. I''ve added this task:\n{0}\nNow you have {1} tasks in the list.\n",
                    todo.getSummary(), store.size());
        }
    },
    DEADLINE("deadline") {
        /**
         * Add a Deadline task. Expected arguments are a goal and date deadline.
         *
         * @param args   command arguments (later parsed inside command)
         * @param store  the task store to operate on
         * @param loader the loader to save / load the store
         */
        @Override
        public String operation(String args, Store store, Loader loader) throws Exception {
            shouldExit = false;
            HashMap<String, String> argMap = Task.parseArgs(args);
            Deadline deadline = new Deadline(argMap);
            store.add(deadline);
            return MessageFormat.format("Got it. I''ve added this task:\n{0}\nNow you have {1} tasks in the list.\n",
                    deadline.getSummary(), store.size());
        }
    },
    EVENT("event") {
        /**
         * Add a Event task. Expected arguments are a goal, date from, and date to.
         *
         * @param args   command arguments (later parsed inside command)
         * @param store  the task store to operate on
         * @param loader the loader to save / load the store
         */
        @Override
        public String operation(String args, Store store, Loader loader) throws Exception {
            shouldExit = false;
            HashMap<String, String> argMap = Task.parseArgs(args);
            Event event = new Event(argMap);
            store.add(event);
            return MessageFormat.format("Got it. I''ve added this task:\n{0}\nNow you have {1} tasks in the list.\n",
                    event.getSummary(), store.size());
        }
    },
    DELETE("delete") {
        /**
         * Delete any task. Expects task index within store.
         *
         * @param args   command arguments (later parsed inside command)
         * @param store  the task store to operate on
         * @param loader the loader to save / load the store
         */
        @Override
        public String operation(String args, Store store, Loader loader) throws Exception {
            shouldExit = false;
            try {
                Task task = store.get(Integer.parseInt(args) - 1);
                String taskSummary = task.getSummary();
                store.remove(Integer.parseInt(args) - 1);
                return MessageFormat.format(
                        "Noted. I''ve removed this task:\n{0}\nNow you have {1} tasks in the list.\n",
                        taskSummary, store.size());
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Not task ID.");
            } catch (IndexOutOfBoundsException e) {
                throw new IndexOutOfBoundsException("Invalid task ID.");
            }
        }
    },
    FIND("find") {
        /**
         * Find tasks with the given query argument in the description
         *
         * @param args   command arguments (later parsed inside command)
         * @param store  the task store to operate on
         * @param loader the loader to save / load the store
         */
        @Override
        public String operation(String args, Store store, Loader loader) throws Exception {
            shouldExit = false;
            String query = args;
            Iterator<Task> storeIt = store.iterator();
            ArrayList<Task> matchedTasks = new ArrayList<>();
            while (storeIt.hasNext()) {
                Task task = storeIt.next();
                if (task.getDescription().contains(query)) {
                    matchedTasks.add(task);
                }
            }
            if (matchedTasks.isEmpty()) {
                return "No matching tasks found.";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Here are the matching tasks in your list:");
            for (int i = 0; i < matchedTasks.size(); i++) {
                Task task = matchedTasks.get(i);
                sb.append(MessageFormat.format("{0}.{1}\n", String.format("%03d", i + 1), task.getSummary()));
            }
            return sb.toString();
        }
    };

    private static HashMap<String, Command> commandMap;
    private static boolean shouldExit;
    private final String command;

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

    /**
     * Parse an input string into a Command (case-insensitive).
     *
     * @param input raw command string
     * @return corresponding Command enum
     * @throws NotCommandException if the input does not match any command
     */
    public static Command fromString(String input) throws NotCommandException {
        if (commandMap.containsKey(input.toLowerCase())) {
            return commandMap.get(input);
        }
        throw new NotCommandException("Not valid command.");
    }

    /**
     * Execute this command with the provided arguments on the given Store and
     * Loader
     *
     * @param args   command arguments (later parsed inside command)
     * @param store  the task store to operate on
     * @param loader the loader to save / load the store
     */
    public abstract String operation(String args, Store store, Loader loader) throws Exception;

    @Override
    public String toString() {
        return this.command;
    }

    public static boolean isExit() {
        return shouldExit;
    }

    /**
     * Change the mark status of the task identified by args.
     * Pwrforms parsing, bounds checking and prints messages.
     *
     * @param args  index (1-based) of the task to change
     * @param store the task store containing tasks
     * @param mark  true to mark as done, false to unmark
     */
    private static String changeMark(String args, Store store, boolean mark)
            throws NumberFormatException, IndexOutOfBoundsException {
        shouldExit = false;
        try {
            Task task = store.get(Integer.parseInt(args) - 1);
            StringBuilder sb = new StringBuilder();
            if (mark) {
                task.mark();
                sb.append("Nice! I've marked this task as done:\n");
            } else {
                task.unmark();
                sb.append("OK, I've marked this task as not done yet:\n");
            }
            sb.append(task.getSummary());
            return sb.toString();
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Not task ID.");
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Invalid task ID.");
        }
    }
}
