package nemo.store;

import nemo.task.Task;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class Store {
    private ArrayList<Task> store;

    public Store() {
        this.store = new ArrayList<>();
    }

    public Task get(int id) {
        return this.store.get(id);
    }

    public void add(Task task) {
        this.store.add(task);
    }

    public void remove(int i) {
        this.store.remove(i);
    }

    public int size() {
        return this.store.size();
    }

    // Solution adapted from https://www.geeksforgeeks.org/java/iterators-in-java/
    public Iterator<Task> iterator() {
        return store.iterator();
    }

    public String generateList() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < store.size(); i++) {
            Task task = store.get(i);
            sb.append(MessageFormat.format("{0}.{1}\n", String.format("%03d", i + 1), task.getSummary()));
        }
        return sb.toString().trim();
    }
}
