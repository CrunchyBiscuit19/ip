package nemo.task;

import java.text.MessageFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
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

/**
 * Task representing an event that has a start ("from") and end ("to") datetime.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Construct an Event.
     *
     * @param goal description of the event
     * @param from start datetime string in "dd-MM-yyyy HHmm" format
     * @param to   end datetime string in "dd-MM-yyyy HHmm" format
     * @param done initial completion state
     * @throws DateTimeParseException if either datetime cannot be parsed
     */
    public Event(String goal, String from, String to, boolean done) throws DateTimeParseException {
        super(goal, done);
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
    }

    public Event(String goal, String from, String to) throws DateTimeParseException {
        this(goal, from, to, false);
    }

    /**
     * Construct an Event from a parsed argument map.
     * Keys "mainArg", "from" and "to" should be present.
     *
     * @param argMap map of parsed arguments
     * @throws ParseException         if required keys are missing or blank
     * @throws DateTimeParseException if datetime parsing fails
     */
    public Event(HashMap<String, String> argMap) throws ParseException, DateTimeParseException {
        super(argMap);
        if (!argMap.containsKey(EventArg.FROM.toString())) {
            throw new ParseException("Task argument requires a from date", 0);
        }
        if (!argMap.containsKey(EventArg.TO.toString())) {
            throw new ParseException("Task argument requires a to date", 0);
        }
        assert (argMap.containsKey(EventArg.FROM.toString()));
        assert (argMap.containsKey(EventArg.TO.toString()));
        this.from = parseDateTime(argMap.get(EventArg.FROM.toString()));
        this.to = parseDateTime(argMap.get(EventArg.TO.toString()));
    }

    /**
     * Return a human-readable description including the event's start and end.
     *
     * @return formatted description string
     */
    @Override
    public String getDescription() {
        return MessageFormat.format("{0} (from: {1} to: {2})", goal,
                dateTimeToString(from),
                dateTimeToString(to));
    }

    @Override
    public String getTypeIcon() {
        return "E";
    }

    /**
     * Return a serialized representation for saving to file.
     *
     * @return serialized task line
     */
    @Override
    public String getSerialized() {
        return MessageFormat.format("{0} | {1} | {2} | {3} | {4}", getTypeIcon(), isDone ? 1 : 0, goal,
                dateTimeToString(from),
                dateTimeToString(to));
    }
}
