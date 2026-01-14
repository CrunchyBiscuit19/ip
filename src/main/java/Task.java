import java.text.MessageFormat;

abstract public class Task {
    protected boolean done;
    protected String goal;

    public Task(String goal) {
        this.goal = goal;
        this.done = false;
    }
    
    abstract public String getDescription(); 

    abstract public String getTypeIcon();

    public String getStatusIcon() {
        return (done ? "X" : " "); // mark done task with X
    }

    public String getSummary() {
        return MessageFormat.format("[{0}][{1}] {2}", getTypeIcon(), getStatusIcon(), getDescription());
    }

    public void mark() {
        done = true;
    }

    public void unmark() {
        done = false;
    }
}