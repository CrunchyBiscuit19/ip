import java.text.MessageFormat;
import java.text.ParseException;
import java.util.HashMap;

enum DeadlineArg {
    BY("by");

    private final String name;

    DeadlineArg(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

public class Deadline extends Task {
    private String by;

    Deadline(HashMap<String, String> argMap) throws ParseException {
        super(argMap);
        if (!argMap.containsKey(DeadlineArg.BY.toString())) {
            throw new ParseException("Task argument requires a by date", 0);
        }
        this.by = argMap.get(DeadlineArg.BY.toString());
    }

    @Override
    public String getDescription() {
        return MessageFormat.format("{0} (by: {1})", goal, by);
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }
}
