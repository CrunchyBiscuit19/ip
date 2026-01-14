import java.text.MessageFormat;

public class Event extends Task {
    private String from;
    private String to;

    Event(String goal, String from, String to) {
        super(goal);
        this.from = from;
        this.to = to;
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
