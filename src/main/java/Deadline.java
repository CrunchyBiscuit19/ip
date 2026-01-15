import java.text.MessageFormat;
import java.text.ParseException;
import java.util.HashMap;

public class Deadline extends Task {
    private String by;

    Deadline(HashMap<String, String> argMap) throws ParseException {
        super(argMap);
        if (!argMap.containsKey("by")) {
            throw new ParseException("Task argument requires a by date", 0);
        }
        this.by = argMap.get("by");
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
