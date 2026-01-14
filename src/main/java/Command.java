
public enum Command {
    BYE("bye");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return this.command;
    }
}
