import java.text.MessageFormat;
import java.text.ParseException;
import java.util.HashMap;

public class Todo extends Task {
    Todo(HashMap<String, String> argMap) throws ParseException {
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
        return MessageFormat.format("{0} | {1} | {2}", getTypeIcon(), done ? 1 : 0, goal);
    }
}
