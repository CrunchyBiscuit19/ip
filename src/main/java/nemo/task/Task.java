package nemo.task;

import java.text.MessageFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

enum TaskArg {
    MAINARG("mainArg");

    private final String name;

    TaskArg(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

/**
 * Abstract base class representing a general task with a goal and status.
 */
abstract public class Task {
    protected boolean done;
    protected String goal;

    public Task(String goal, boolean done) {
        this.goal = goal;
        this.done = done;
    }

    public Task(String goal) {
        this(goal, false);
    }

    /**
     * Construct a Task from a parsed argument map. 
     * Main argument key "mainArg" contains the task goal.
     *
     * @param argMap map of parsed arguments
     * @throws ParseException if the required main argument is blank
     */
    public Task(HashMap<String, String> argMap) throws ParseException {
        if (argMap.get(TaskArg.MAINARG.toString()).isBlank()) {
            throw new ParseException("Task argument requires a goal", 0);
        }
        this.goal = argMap.get(TaskArg.MAINARG.toString());
        this.done = false;
    }

    /**
     * Parse a raw arguments string into a map of argument keys to values.
     * Main argument is stored under the "mainArg" key. 
     * All other keys are created when tokens start with '/' are encountered.
     *
     * @param args raw argument string
     * @return map of argument keys
     * @throws ParseException if the args string is blank
     */
    public static HashMap<String, String> parseArgs(String args) throws ParseException {
        if (args.isBlank()) {
            throw new ParseException("Task argument cannot be blank.", 0);
        }

        HashMap<String, StringBuilder> argBuilderMap = new HashMap<>();
        String[] argTokens = args.split(" ");
        String lastKey = TaskArg.MAINARG.toString();
        argBuilderMap.put(lastKey, new StringBuilder());
        for (String token : argTokens) {
            if (token.startsWith("/")) {
                lastKey = token.substring(1);
                argBuilderMap.put(lastKey, new StringBuilder());
            } else {
                argBuilderMap.get(lastKey).append(token).append(" ");
            }
        }

        HashMap<String, String> argMap = new HashMap<>();
        for (HashMap.Entry<String, StringBuilder> entry : argBuilderMap.entrySet()) {
            argMap.put(entry.getKey(), entry.getValue().toString().trim());
        }

        return argMap;
    }
    
    /**
     * Return a human-readable description of the task.
     *
     * @return task description string
     */
    abstract public String getDescription(); 

    /**
     * Return a one-letter icon representing the task type.
     *
     * @return task type icon
     */
    abstract public String getTypeIcon();

    /**
     * Return a serialized representation of the task.
     *
     * @return serialized task string
     */
    abstract public String getSerialized();

    public String getStatusIcon() {
        return (done ? "X" : " "); // mark done task with X
    }

    /**
     * Return the summary of this task
     *
     * @return summary string of the task
     */
    public String getSummary() {
        return MessageFormat.format("[{0}][{1}] {2}", getTypeIcon(), getStatusIcon(), getDescription());
    }

    public void mark() {
        done = true;
    }

    public void unmark() {
        done = false;
    }

    @Override
    public String toString() {
        return getSummary();
    }

    /**
     * Parse a string into a LocalDateTime format.
     * 
     * @param rawDateTimeString The raw string to be parsed
     * @return A LocalDateTime that is parsed from the date passed in.
     * @throws DateTimeParseException when the raw string is not in the expected date time patten.
     */
    protected static LocalDateTime parseDateTime(String rawDateTimeString) throws DateTimeParseException {
        // Solution adapted from https://stackoverflow.com/a/22463063
        String pattern = "dd-MM-yyyy HHmm";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDateTime.parse(rawDateTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException(MessageFormat.format("{0} is not a valid date for format {1}.", rawDateTimeString, pattern), pattern, 0);
        }
    }
}