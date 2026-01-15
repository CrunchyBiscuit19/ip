import java.text.MessageFormat;
import java.text.ParseException;
import java.util.HashMap;

public class Event extends Task {
    private String from;
    private String to;

    Event(HashMap<String, String> argMap) throws ParseException {
        super(argMap);
        if (!argMap.containsKey("from")) {
            throw new ParseException("Task argument requires a from date", 0);
        }
        if (!argMap.containsKey("to")) {
            throw new ParseException("Task argument requires a to date", 0);
        }
        this.from = argMap.get("from");
        this.to = argMap.get("to");
    }

    @Override
    public String getDescription() {
        return MessageFormat.format("{0} (from: {1}, to: {2})", goal, from, to);
    }

    @Override
    public String getTypeIcon() {
        return "E";
    }
}
