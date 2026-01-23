import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

public class Loader {
    private Path saveFilePath;

    public Loader(Path saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    void load(Store store) {
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

    void save(Store store) {        
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
            System.out.println("Something went wrong while saving your file.");
        }
    }

    
}
