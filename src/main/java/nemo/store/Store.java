package nemo.store;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    /**
     * Add multiple to tasks to the store at the same time
     *
     * @param tasks
     *            Var args of tasks
     */
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

    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    // Solution adapted from https://www.geeksforgeeks.org/java/iterators-in-java/
    public Iterator<Task> getIterator() {
        return tasks.iterator();
    }

    /**
     * Sort the priorities by their rank
     *
     * @param shouldSortHighestPriorityFirst Whether to sort highest priorities at the top
     */
    public void sortByPriority(boolean shouldSortHighestPriorityFirst) {
        if (shouldSortHighestPriorityFirst) {
            tasks.sort(Comparator.comparing(Task::getPriority));
        } else {
            tasks.sort(Comparator.comparing(Task::getPriority).reversed());
        }
    }

    /**
     * Generate nicely formatted list of all tasks for printing.
     *
     * @return list of task summaries
     */
    public String generateList() {
        return generateListFormattedTasks(tasks);
    }

    /**
     * Find tasks whioh contain the query line
     *
     * @param query
     *            string to match tasks against
     * @return The list of tasks which matched the query
     */
    public ArrayList<Task> findTasks(String query) {
        Iterator<Task> tasksIt = this.getIterator();
        ArrayList<Task> matchedTasks = new ArrayList<>();
        while (tasksIt.hasNext()) {
            Task task = tasksIt.next();
            if (task.getDescription().contains(query)) {
                matchedTasks.add(task);
            }
        }
        return matchedTasks;
    }

    /**
     * Generate nicely formatted list of certain tasks for printing.
     *
     * @param tasksList
     *            List to iterate through
     * @return list of task summaries
     */
    public static String generateListFormattedTasks(ArrayList<Task> tasksList) {
        return IntStream.range(0, tasksList.size())
                .mapToObj(i -> {
                    Task task = tasksList.get(i);
                    String threeDigitIndex = String.format("%03d", i + 1);
                    return MessageFormat.format("{0}.{1}\n", threeDigitIndex, task.getSummary());
                })
                .collect(Collectors.joining())
                .trim();
    }
}
