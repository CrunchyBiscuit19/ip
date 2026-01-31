package nemo.gui;

import java.text.MessageFormat;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import nemo.command.Command;
import nemo.store.Loader;
import nemo.store.Store;

/**
 * Controller for the main GUI.
 */
public class Controller extends AnchorPane {
    private Store store;
    private Loader loader;

    @FXML
    private HBox notificationBar;
    @FXML
    private Label notificationLabel;
    @FXML
    private Button notificationButton;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image nemoImage = new Image(this.getClass().getResourceAsStream("/images/nemo.png"));

    /**
     * Intialize what's needed
     */
    @FXML
    public void initialize() {
        notificationBar.setVisible(false);
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Assign store and loader, and attempt to load save file.
     *
     * @param store  assigned store to this Nemo
     * @param loader assigned loader to this Nemo
     */
    public void setStoreLoader(Store store, Loader loader) {
        this.store = store;
        this.loader = loader;
        try {
            loader.load(store);
        } catch (Exception e) {
            showNotification(e.getMessage());
        }
    }

    /***
     * Setup notification
     *
     * @param text string inside the notification
     */
    @FXML
    private void showNotification(String text) {
        notificationBar.setVisible(true);
        notificationLabel.setText(text);
    }

    /**
     * Makes the notification bar go away after it's clicked to dismiss
     */
    @FXML
    private void dismissNotification() {
        notificationBar.setVisible(false);
    }

    /**
     * Parse command and arguments, execute command, then write
     * back results into dialog box for user and nemo.
     *
     * @param input the raw input line from the user
     */
    @FXML
    private void processInput() {
        String input = userInput.getText();
        String[] splitInput = input.split(" ", 2); // Limit to 2 split strings, command and arguments
        String commandStr = splitInput[0]; // Get command
        String args = splitInput.length >= 2 ? splitInput[1] : ""; // Get rest of argument, or empty string if not
        // available
        String nemoText = "Placeholder";
        try {
            nemoText = Command.fromString(commandStr).operation(args, store, loader);
        } catch (Exception e) {
            nemoText = MessageFormat.format("ERROR | {0}", e.getMessage());
        }

        dialogContainer.getChildren().addAll(
                DialogBox.createUserDialog(input, userImage),
                DialogBox.createNemoDialog(nemoText, nemoImage));
        userInput.clear();

        if (Command.isExit()) {
            try {
                loader.save(store);
            } catch (Exception e) {
                showNotification(e.getMessage());
            } finally {
                // Solution adapted from https://stackoverflow.com/a/30543838
                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(event -> Platform.exit());
                delay.play();
            }
        }
    }
}
