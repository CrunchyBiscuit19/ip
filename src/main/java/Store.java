import java.text.MessageFormat;
import java.util.ArrayList;

public class Store {
    private ArrayList<Task> store;

    Store() {
        this.store = new ArrayList<>();
    }

    Task get(int id) {
        return this.store.get(id);
    }

    void add(Task task) {
        this.store.add(task);
    }

    int size() {
        return this.store.size();
    }

    String generateList() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < store.size(); i++) {
            Task task = store.get(i);
            sb.append(MessageFormat.format("{0}.{1}\n", String.format("%03d", i + 1), task.getSummary()));
        }
        return sb.toString().trim();
    }
}
