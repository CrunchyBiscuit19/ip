package nemo.store;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import nemo.task.Deadline;
import nemo.task.Event;
import nemo.task.Task;
import nemo.task.Todo;

/**
 * Responsible for loading tasks from and saving tasks to a save file.
 * Tasks are serialized as single lines when saving and converted
 * back to Task objects when loading.
 */
public class Loader {
    private Path saveFilePath;

    public Loader(Path saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    public Loader(String saveFilePath) {
        this.saveFilePath = Paths.get(saveFilePath);
    }

    /**
     * Read tasks from the save file and populate the store.
     * If save file doesn't exist, print message informing user.
     *
     * @param store the store to load tasks into
     */
    public void load(Store store) throws IOException {
        ArrayList<String> rawTasks = new ArrayList<>();
        try {
            rawTasks = new ArrayList<>(Files.lines(saveFilePath).toList());
        } catch (IOException e) {
            throw new IOException("Save file does not exist. Create tasks and save them to create one.");
        }

        for (String rawTask : rawTasks) {
            String[] data = rawTask.split("\\|");
            String type = data[0].trim();
            boolean done = data[1].trim().equals("1") ? true : false;
            String goal = data[2].trim();

            Task newTask = new Todo("");
            if (type.equals("T")) {
                newTask = new Todo(goal, done);
            } else if (type.equals("D")) {
                String by = data[3].trim();
                newTask = new Deadline(goal, by, done);
            } else if (type.equals("E")) {
                String from = data[3].trim();
                String to = data[4].trim();
                newTask = new Event(goal, from, to, done);
            }
            store.add(newTask);
        }
    }

    /**
     * Save the tasks in the store to the save file.
     * Save file and directories automatically created if necessary.
     *
     * @param store the store to save the tasks
     */
    public void save(Store store) throws Exception {
        StringBuilder sb = new StringBuilder();
        Iterator<Task> storeIt = store.iterator();
        while (storeIt.hasNext()) {
            Task task = storeIt.next();
            sb.append(task.getSerialized());
            sb.append('\n');
        }

        try {
            Files.createDirectories(saveFilePath.getParent());
            Files.writeString(saveFilePath, sb.toString().trim());
        } catch (Exception e) {
            throw new IOException("Something went wrong while saving your file.");
        }
    }

}
