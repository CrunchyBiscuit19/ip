package nemo.store;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import nemo.task.Priority;
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
        assert this.tasks != null : "Store tasks list is null";
        assert id >= 0 && id < this.tasks.size() : "Index out of bounds: " + id;
        return this.tasks.get(id);
    }

    /**
     * Add a new task to the tasks list
     *
     * @param task The new task to be added
     */
    public void add(Task task) {
        assert task != null : "task cannot be null";
        this.tasks.add(task);
    }

    /**
     * Add multiple to tasks to the store at the same time
     *
     * @param tasks
     *            Var args of tasks
     */
    public void add(Task... tasks) {
        assert tasks != null : "tasks varargs cannot be null";
        for (int i = 0; i < tasks.length; i++) {
            assert tasks[i] != null : "Task at index " + i + " is null";
            this.tasks.add(tasks[i]);
        }
    }

    /**
     * Remove a task at an index in the store tasks list
     *
     * @param i The index to remove the task from the list
     */
    public void remove(int i) {
        assert this.tasks != null : "Store tasks list is null";
        assert i >= 0 && i < this.tasks.size() : "Index out of bounds: " + i;
        this.tasks.remove(i);
    }

    /**
     * Get the size of the store tasks list
     *
     * @return size of the store tasks list
     */
    public int size() {
        assert this.tasks != null : "Store tasks list is null";
        return this.tasks.size();
    }

    /**
     * Check if the store tasks list is empty or not
     *
     * @return boolean if the store tasks list is empty or not
     */
    public boolean isEmpty() {
        assert this.tasks != null : "Store tasks list is null";
        return this.tasks.isEmpty();
    }

    public Iterator<Task> getIterator() {
        assert this.tasks != null : "Store tasks list is null";
        // @@author CrunchyBiscuit19-reused
        // Reused from https://www.geeksforgeeks.org/java/iterators-in-java/
        // with minor modifications
        return tasks.iterator();
        // @@author
    }

    /**
    * Filter the tasks by their priorities
    *
    * @param filteredPriority Priority to keep
    */
    public ArrayList<Task> filterByPriority(Priority filteredPriority) {
        assert filteredPriority != null : "filteredPriority cannot be null";
        assert this.tasks != null : "Store tasks list is null";
        return new ArrayList<>(tasks.stream()
                .filter(t -> {
                    assert t != null : "task in store is null";
                    return t.getPriority() == filteredPriority;
                })
                .toList());
    }

    /**
     * Sort the tasks by their priorities
     *
     * @param shouldSortHighestPriorityFirst Whether to sort highest priorities at the top
     */
    public void sortByPriority(boolean shouldSortHighestPriorityFirst) {
        assert this.tasks != null : "Store tasks list is null";
        assert tasks.stream().allMatch(t -> t != null && t.getPriority() != null) : "Found task with null priority";
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
        assert this.tasks != null : "Store tasks list is null";
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
        assert query != null : "query cannot be null";
        Iterator<Task> tasksIt = this.getIterator();
        ArrayList<Task> matchedTasks = new ArrayList<>();
        while (tasksIt.hasNext()) {
            Task task = tasksIt.next();
            assert task != null : "task in store is null";
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
        assert tasksList != null : "tasksList cannot be null";
        return IntStream.range(0, tasksList.size())
                .mapToObj(i -> {
                    Task task = tasksList.get(i);
                    assert task != null : "task at index " + i + " is null";
                    String threeDigitIndex = String.format("%03d", i + 1);
                    return MessageFormat.format("{0}.{1}\n", threeDigitIndex, task.getSummary());
                })
                .collect(Collectors.joining())
                .trim();
    }
}
