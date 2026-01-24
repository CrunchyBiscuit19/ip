package nemo;

import nemo.command.Command;
import nemo.store.Store;
import nemo.store.Loader;
import nemo.ui.Ui;
import java.nio.file.Path;

public class Nemo {
    private Store store;
    private Loader loader;

    public Nemo(Path saveFilePath) {
        store = new Store();
        loader = new Loader(saveFilePath);
        loader.load(store);
    }

    public void run() {
        Ui.showWelcomeMessage();

        while (!Command.isExit()) {
            String input = Ui.getNextInput();
            Ui.processInput(input, store, loader);
        }

        Ui.cleanup();
    }

    public static void main(String[] args1) {
        new Nemo(Path.of(System.getProperty("user.home"), "NEMO", "data.txt")).run();
    }
}
