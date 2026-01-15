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
}
