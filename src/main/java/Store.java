import java.util.ArrayList;

public class Store {
    private ArrayList<String> store;
    
    Store () {
        this.store = new ArrayList<>();
    }

    void add(String item) {
        this.store.add(item);
    }
}
