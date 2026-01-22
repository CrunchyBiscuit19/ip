import java.text.MessageFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

enum EventArg {
    FROM("from"),
    TO("to");

    private final String name;

    EventArg(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String goal, String from, String to, boolean done) throws DateTimeParseException {
        super(goal, done);
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
    }

    public Event(String goal, String from, String to) throws DateTimeParseException {
        this(goal, from, to, false);
    }

    public Event(HashMap<String, String> argMap) throws ParseException, DateTimeParseException {
        super(argMap);
        if (!argMap.containsKey(EventArg.FROM.toString())) {
            throw new ParseException("Task argument requires a from date", 0);
        }
        if (!argMap.containsKey(EventArg.TO.toString())) {
            throw new ParseException("Task argument requires a to date", 0);
        }
        this.from = parseDateTime(argMap.get(EventArg.FROM.toString()));
        this.to = parseDateTime(argMap.get(EventArg.TO.toString()));
    }

    @Override
    public String getDescription() {
        return MessageFormat.format("{0} (from: {1} to: {2})", goal, from, to);
    }

    @Override
    public String getTypeIcon() {
        return "E";
    }

    @Override
    public String getSerialized() {
        return MessageFormat.format("{0} | {1} | {2} | {3} | {4}", getTypeIcon(), done ? 1 : 0, goal,
                from.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm")),
                to.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm")));
    }
}
