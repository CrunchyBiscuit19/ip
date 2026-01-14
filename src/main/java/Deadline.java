import java.text.MessageFormat;

public class Deadline extends Task {
    private String by;

    Deadline(String goal, String by) {
        super(goal);
        this.by = by;
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
