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

    void add(String itemDescription) {
        this.store.add(new Task(itemDescription));
    }

    String generateList() {
        if (this.store.isEmpty())
            return "Nothing added.";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < store.size(); i++) {
            sb.append(MessageFormat.format("{0}.[{1}] {2}\n", String.format("%03d", i + 1),
                    store.get(i).getStatusIcon(), store.get(i).description));
        }
        return sb.toString().trim();
    }
}
