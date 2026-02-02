package nemo.store;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;

import nemo.task.Task;

/**
 * Collection of Task objects.
 */
public class Store {
    private ArrayList<Task> tasks;

    public Store() {
        this.tasks = new ArrayList<>();
    }

    public Task get(int id) {
        return this.tasks.get(id);
    }

    public void add(Task task) {
        this.tasks.add(task);
    }

    public void add(Task... tasks) {
        for (int i = 0; i < tasks.length; i++) {
            this.tasks.add(tasks[i]);
        }
    }

    public void remove(int i) {
        this.tasks.remove(i);
    }

    public int size() {
        return this.tasks.size();
    }

    // Solution adapted from https://www.geeksforgeeks.org/java/iterators-in-java/
    public Iterator<Task> getIterator() {
        return tasks.iterator();
    }

    /**
     * Generate nicely formatted list of tasks for printing.
     *
     * @return list of task summaries
     */
    public String generateList() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            sb.append(MessageFormat.format("{0}.{1}\n", String.format("%03d", i + 1), task.getSummary()));
        }
        return sb.toString().trim();
    }
}
