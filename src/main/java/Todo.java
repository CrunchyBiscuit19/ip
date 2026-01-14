public class Todo extends Task {
    Todo(String goal) {
        super(goal);
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
