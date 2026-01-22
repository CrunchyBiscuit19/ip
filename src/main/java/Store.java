import java.text.MessageFormat;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;

public class Store {
    private ArrayList<Task> store;

    Store() {
        this.store = new ArrayList<>();
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

    void save() {        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < store.size(); i++) {
            Task task = store.get(i);
            sb.append(task.getSerialized());
            sb.append('\n');
        }

        Path saveFilePath = Path.of(System.getProperty("user.home"), "NEMO", "data.txt");
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
