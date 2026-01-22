import java.text.MessageFormat;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Store {
    private ArrayList<Task> store;
    private static Path saveFilePath = Path.of(System.getProperty("user.home"), "NEMO", "data.txt");

    Store() {
        this.store = new ArrayList<>();
        load();
    }

    Task get(int id) {
        return this.store.get(id);
    }

    void add(Task task) {
        this.store.add(task);
    }

    void remove(int i) {
        this.store.remove(i);
    }

    int size() {
        return this.store.size();
    }

    void load() {
        ArrayList<String> rawTasks = new ArrayList<>();
        try {
            rawTasks = new ArrayList<>(Files.lines(saveFilePath).toList());
        } catch (IOException e) {
            System.out.println("Save file does not exist. Create tasks and save them to create one.");
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

    void save() {        
        StringBuilder sb = new StringBuilder();
        for (Task task : store) {
            sb.append(task.getSerialized());
            sb.append('\n');
        }

        try {
            Files.createDirectories(saveFilePath.getParent());
            Files.writeString(saveFilePath, sb.toString().trim());
        } catch (Exception e) {
            System.out.println("Something went wrong while saving your file.");
        }
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
