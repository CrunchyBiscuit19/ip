package nemo.task;

import java.text.MessageFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
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

/**
 * Task representing a deadline with a single due datetime.
 */
public class Deadline extends Task {
    private static final String SAVE_LINE_FORMAT = "{0} | {1} | {2} | {3} | {4}";
    private LocalDateTime by;

    /**
     * Construct a Deadline with goal and by datetime.
     *
     * @param goal
     *            description of the deadline
     * @param by
     *            due datetime string in "dd-MM-yyyy HHmm" format
     * @param done
     *            initial completion state
     * @throws DateTimeParseException
     *             if the datetime cannot be parsed
     */
    public Deadline(String goal, String by, boolean done, Priority priority) throws DateTimeParseException {
        super(goal, done, priority);
        this.by = parseDateTime(by);
    }

    public Deadline(String goal, String by, Priority priority) throws DateTimeParseException {
        this(goal, by, false, priority);
    }

    /**
     * Construct a Deadline from a parsed argument map.
     * Keys "mainArg" and "by" should be present.
     *
     * @param argMap
     *            map of parsed arguments
     * @throws ParseException
     *             if required keys are missing or blank
     * @throws DateTimeParseException
     *             if datetime parsing fails
     */
    public Deadline(HashMap<String, String> argMap) throws ParseException, DateTimeParseException {
        super(argMap);
        if (!argMap.containsKey(DeadlineArg.BY.toString())) {
            throw new ParseException("Task argument requires a by date", 0);
        }
        assert (argMap.containsKey(DeadlineArg.BY.toString()));
        this.by = parseDateTime(argMap.get(DeadlineArg.BY.toString()));
    }

    /**
     * Return a human-readable description including the due datetime.
     *
     * @return formatted description string
     */
    @Override
    public String getDescription() {
        return MessageFormat.format("{0} (by: {1})", goal, dateTimeToString(by));
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    /**
     * Return a serialized representation for saving to file.
     *
     * @return serialized task line
     */
    @Override
    public String getSerialized() {
        return MessageFormat.format(SAVE_LINE_FORMAT, priority.toString(), getTypeIcon(), isDone ? 1 : 0, goal,
                dateTimeToString(by));
    }
}
