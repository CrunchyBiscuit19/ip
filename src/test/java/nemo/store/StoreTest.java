package nemo.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nemo.store.Store;

public class StoreTest {
    private static final String LOW_TEXT = "a";
    private static final String MED_TEXT = "b";
    private static final String HIGH_TEXT = "c";

    @Test
    public void storeFilterByPriority_tasksVaryingPriority_taskOfSpecifiedPriorityReturned() {
        Store store = new Store();
        store.add(new Todo(LOW_TEXT, Priority.LOW));
        store.add(new Todo(MED_TEXT, Priority.MED));
        store.add(new Todo(MED_TEXT, Priority.MED));
        store.add(new Todo(HIGH_TEXT, Priority.HIGH));
        store.add(new Todo(HIGH_TEXT, Priority.HIGH));
        store.add(new Todo(HIGH_TEXT, Priority.HIGH));

        for (Task task : store.filterByPriority(Priority.LOW)) {
            assertEquals(task.getGoal(), LOW_TEXT);
        }
        for (Task task : store.filterByPriority(Priority.MED)) {
            assertEquals(task.getGoal(), MED_TEXT);
        }
        for (Task task : store.filterByPriority(Priority.HIGH)) {
            assertEquals(task.getGoal(), HIGH_TEXT);
        }
    }
}
