package nemo.task;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.HashMap;

/**
 * Task representing a Todo with a goal and a completion status
 */
public class Todo extends Task {
    private static final String SAVE_LINE_FORMAT = "{0} | {1} | {2} | {3}";

    public Todo(String goal, boolean done, Priority priority) {
        super(goal, done, priority);
    }

    public Todo(String goal, Priority priority) {
        this(goal, false, priority);
    }

    public Todo(HashMap<String, String> argMap) throws ParseException {
        super(argMap);
    }

    @Override
    public String getDescription() {
        return goal;
    }

    @Override
    public String getTypeIcon() {
        return "T";
    }

    @Override
    public String getSerialized() {
        return MessageFormat.format(SAVE_LINE_FORMAT, priority.toString(), getTypeIcon(), isDone ? 1 : 0, goal);
    }
}
