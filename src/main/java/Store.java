import java.text.MessageFormat;
import java.util.ArrayList;

public class Store {
    private ArrayList<String> store;
    
    Store () {
        this.store = new ArrayList<>();
    }

    void add(String item) {
        this.store.add(item);
    }

    String generateList() {
        if (this.store.isEmpty()) return "Nothing added.";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < store.size(); i++) {
            sb.append(MessageFormat.format("{0}. {1}\n", String.format("%03d", i + 1), store.get(i)));
        }
        return sb.toString().trim();
    }
}
