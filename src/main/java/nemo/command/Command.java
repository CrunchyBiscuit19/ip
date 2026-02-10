package nemo.command;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import nemo.exception.NotCommandException;
import nemo.store.Loader;
import nemo.store.Store;
import nemo.task.Deadline;
import nemo.task.Event;
import nemo.task.Priority;
import nemo.task.Task;
import nemo.task.Todo;

/**
 * Enum representing all supported user commands and their associated operations
 * Each enum constant implements the operation method to perform its behavior.
 */
public enum Command {
    // Solution below inspired by https://stackoverflow.com/a/14968372
    BYE("bye") {
        /**
         * Leaves the conversation.
         *
         * @param args   command arguments (later parsed inside command)
         * @param store  the task store to operate on
         * @param loader the loader to save / load the store
         */
        @Override
        public String operate(String args, Store store, Loader loader) throws Exception {
            shouldExit = true;
            return "Bye.";
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
        public String operate(String args, Store store, Loader loader) throws Exception {
            shouldExit = false;
            if (store.isEmpty()) {
                return "Nothing in the list yet.";
            }
            String filterKey = "filter";
            HashMap<String, String> argMap = Task.parseArgs(args);
            if (argMap.containsKey(filterKey)) {
                String filter = argMap.get(filterKey).trim().toUpperCase();
                Priority filteredPriority = Priority.fromString(filter);
                ArrayList<Task> filteredTasksList = store.filterByPriority(filteredPriority);
                if (filteredTasksList.isEmpty()) {
                    return "Nothing in the filtered list.";
                }
                return MessageFormat.format("Here are the tasks in your filtered list:\n{0}",
                        Store.generateListFormattedTasks(filteredTasksList));
            } else {
                return MessageFormat.format("Here are the tasks in your list:\n{0}", store.generateList());
            }
        }
    },
    SORT("sort") {
        /**
         * Sorts all the tasks in the store by their priorities.
         *
         * @param args   command arguments (later parsed inside command)
         * @param store  the task store to operate on
         * @param loader the loader to save / load the store
         */
        @Override
        public String operate(String args, Store store, Loader loader) throws Exception {
            String directionKey = "direction";
            HashMap<String, String> argMap = Task.parseArgs(args);
            if (!argMap.containsKey(directionKey)) {
                throw new ParseException("Sorting requires direction", 0);
            }
            assert (argMap.containsKey(directionKey));
            String direction = argMap.get(directionKey).trim().toUpperCase();
            if (!direction.equals("UP") && !direction.equals("DOWN")) {
                throw new IllegalArgumentException("Invalid sorting direction.");
            }
            switch (direction) {
            case "UP":
                store.sortByPriority(true);
                break;
            case "DOWN":
            default:
                store.sortByPriority(false);
                break;
            }
            return "Your tasks have been sorted by priorities.";
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
        public String operate(String args,
                Store store, Loader loader) throws Exception {
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
        public String operate(String args, Store store, Loader loader) throws Exception {
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
        public String operate(String args, Store store, Loader loader) throws Exception {
            shouldExit = false;
            HashMap<String, String> argMap = Task.parseArgs(args);
            Todo todo = new Todo(argMap);
            store.add(todo);
            return MessageFormat.format("I''ve added this task:\n{0}\nNow you have {1} tasks in the list.\n",
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
        public String operate(String args, Store store, Loader loader) throws Exception {
            shouldExit = false;
            HashMap<String, String> argMap = Task.parseArgs(args);
            Deadline deadline = new Deadline(argMap);
            store.add(deadline);
            return MessageFormat.format("I''ve added this task:\n{0}\nNow you have {1} tasks in the list.\n",
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
        public String operate(String args, Store store, Loader loader) throws Exception {
            shouldExit = false;
            HashMap<String, String> argMap = Task.parseArgs(args);
            Event event = new Event(argMap);
            store.add(event);
            return MessageFormat.format("I''ve added this task:\n{0}\nNow you have {1} tasks in the list.\n",
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
        public String operate(String args, Store store, Loader loader) throws Exception {
            shouldExit = false;
            try {
                Task task = store.get(Integer.parseInt(args) - 1);
                String taskSummary = task.getSummary();
                store.remove(Integer.parseInt(args) - 1);
                return MessageFormat.format(
                        "I''ve removed this task:\n{0}\nNow you have {1} tasks in the list.\n", taskSummary,
                        store.size());
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
        public String operate(String args, Store store, Loader loader) throws Exception {
            shouldExit = false;
            String query = args;
            ArrayList<Task> matchedTasks = store.findTasks(query);
            if (matchedTasks.isEmpty()) {
                return "No matching tasks found.";
            }
            return Store.generateListFormattedTasks(matchedTasks);
        }
    };

    private static HashMap<String, Command> commandMap;
    private static boolean shouldExit;
    private final String command;

    Command(String command) {
        this.command = command;
    }

    // Solution below inspired by https://stackoverflow.com/a/25411863
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
    public abstract String operate(String args, Store store, Loader loader) throws Exception;

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
                sb.append("I've marked this task as done:\n");
            } else {
                task.unmark();
                sb.append("I've marked this task as not done yet:\n");
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
