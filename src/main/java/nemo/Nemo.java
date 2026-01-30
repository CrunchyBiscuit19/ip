package nemo;

import java.nio.file.Path;

import javafx.application.Application;
import nemo.command.Command;
import nemo.gui.Gui;
import nemo.store.Loader;
import nemo.store.Store;
import nemo.ui.Ui;

/**
 * The main class that encompasses the whole program.
 */
public class Nemo {
    private Store store;
    private Loader loader;

    /**
     * Creates a store and loader from a save file.
     * Store is loaded from save file data, if the save file exists.
     *
     * @param saveFilePath path to the save file on the system
     */
    public Nemo(Path saveFilePath) {
        store = new Store();
        loader = new Loader(saveFilePath);
        loader.load(store);
    }

    /**
     * Responsible for running the main UI loop.
     * Takes input for each loop and processes it.
     * Cleanup when command loop is exited.
     */
    public void run() {

        Ui.showWelcomeMessage();

        while (!Command.isExit()) {
            String input = Ui.getNextInput();
            Ui.processInput(input, store, loader);
        }

        Ui.cleanup();
    }

    public static void main(String[] args1) {
        Application.launch(Gui.class, args1);
    }
}
