package nemo.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import nemo.store.Store;

public class StoreTest {
    private static final String LOW_TEXT = "a";
    private static final int LOW_COUNT = 1;
    private static final String MED_TEXT = "b";
    private static final int MED_COUNT = 2;
    private static final String HIGH_TEXT = "c";
    private static final int HIGH_COUNT = 3;

    @Test
    public void storeFilterByPriority_tasksVaryingPriority_taskOfSpecifiedPriorityReturned() {
        Store store = new Store();
        store.add(new Todo(LOW_TEXT, Priority.LOW));
        store.add(new Todo(MED_TEXT, Priority.MED));
        store.add(new Todo(MED_TEXT, Priority.MED));
        store.add(new Todo(HIGH_TEXT, Priority.HIGH));
        store.add(new Todo(HIGH_TEXT, Priority.HIGH));
        store.add(new Todo(HIGH_TEXT, Priority.HIGH));

        ArrayList<Task> lowPriorityTasks = store.filterByPriority(Priority.LOW);
        assertEquals(lowPriorityTasks.size(), LOW_COUNT);
        for (Task task : lowPriorityTasks) {
            assertEquals(task.getGoal(), LOW_TEXT);
        }
        ArrayList<Task> medPriorityTasks = store.filterByPriority(Priority.MED);
        assertEquals(medPriorityTasks.size(), MED_COUNT);
        for (Task task : store.filterByPriority(Priority.MED)) {
            assertEquals(task.getGoal(), MED_TEXT);
        }
        ArrayList<Task> highPriorityTasks = store.filterByPriority(Priority.HIGH);
        assertEquals(highPriorityTasks.size(), HIGH_COUNT);
        for (Task task : store.filterByPriority(Priority.HIGH)) {
            assertEquals(task.getGoal(), HIGH_TEXT);
        }
    }
}
