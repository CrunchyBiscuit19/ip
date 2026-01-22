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

    public Task(HashMap<String, String> argMap) throws ParseException {
        if (argMap.get(TaskArg.MAINARG.toString()).isBlank()) {
            throw new ParseException("Task argument requires a goal", 0);
        }
        this.goal = argMap.get(TaskArg.MAINARG.toString());
        this.done = false;
    }

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
    
    abstract public String getDescription(); 

    abstract public String getTypeIcon();

    abstract public String getSerialized();

    public String getStatusIcon() {
        return (done ? "X" : " "); // mark done task with X
    }

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