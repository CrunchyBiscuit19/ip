import java.text.MessageFormat;
import java.text.ParseException;
import java.util.HashMap;

abstract public class Task {
    protected boolean done;
    protected String goal;

    public Task(HashMap<String, String> argMap) throws ParseException {
        if (!argMap.containsKey("mainArg")) {
            throw new ParseException("Task argument requires a goal", 0);
        }
        this.goal = argMap.get("mainArg");
        this.done = false;
    }

    public static HashMap<String, String> parseArgs(String args) throws ParseException {
        if (args.isBlank()) {
            throw new ParseException("Task argument cannot be blank.", 0);
        }

        HashMap<String, StringBuilder> argBuilderMap = new HashMap<>();
        String[] argTokens = args.split(" ");
        String lastKey = "mainArg";
        for (String token : argTokens) {
            if (token.startsWith("/")) {
                lastKey = token.substring(1);
                argBuilderMap.put(lastKey, new StringBuilder());
            } else {
                argBuilderMap.get(lastKey).append(token);
            }
        }

        HashMap<String, String> argMap = new HashMap<>();
        for (HashMap.Entry<String, StringBuilder> entry : argBuilderMap.entrySet()) {
            argMap.put(entry.getKey(), entry.getValue().toString());
        }

        return argMap;
    }
    
    abstract public String getDescription(); 

    abstract public String getTypeIcon();

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
}