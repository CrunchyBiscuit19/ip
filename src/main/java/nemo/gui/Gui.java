package nemo.gui;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nemo.store.Loader;
import nemo.store.Store;

/**
 * Responsible for running GUI above Nemo.
 */
public class Gui extends Application {
    @Override
    public void start(Stage stage) {
        List<String> args = getParameters().getRaw();
        Store store = new Store();
        Loader loader = new Loader(args.get(0));
        loader.load(store);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource("/view/Controller.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<Controller>getController().setStoreLoader(store, loader);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
