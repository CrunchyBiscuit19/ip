package nemo.task;

import java.text.MessageFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    private LocalDateTime by;

    public Deadline(String goal, String by, boolean done) throws DateTimeParseException {
        super(goal, done);
        this.by = parseDateTime(by);
    }
    
    public Deadline(String goal, String by) throws DateTimeParseException {
        this(goal, by, false);
    }

    public Deadline(HashMap<String, String> argMap) throws ParseException, DateTimeParseException {
        super(argMap);
        if (!argMap.containsKey(DeadlineArg.BY.toString())) {
            throw new ParseException("Task argument requires a by date", 0);
        }
        this.by = parseDateTime(argMap.get(DeadlineArg.BY.toString()));
    }

    @Override
    public String getDescription() {
        return MessageFormat.format("{0} (by: {1})", goal, by.format(DateTimeFormatter.ofPattern("d MMMM yyyy ha")));
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    @Override
    public String getSerialized() {
        return MessageFormat.format("{0} | {1} | {2} | {3}", getTypeIcon(), done ? 1 : 0, goal, by.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm")));
    }
}
