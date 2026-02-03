package nemo.store;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import nemo.task.Task;

/**
 * Collection of Task objects.
 */
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

    public void add(Task... tasks) {
        for (int i = 0; i < tasks.length; i++) {
            this.store.add(tasks[i]);
        }
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

    /**
     * Generate nicely formatted list of tasks for printing.
     *
     * @return list of task summaries
     */
    public String generateList() {
        return IntStream.range(0, store.size())
                .mapToObj(i -> {
                    Task task = store.get(i);
                    return MessageFormat.format("{0}.{1}\n", String.format("%03d", i + 1), task.getSummary());
                })
                .collect(Collectors.joining())
                .trim();
    }
}
