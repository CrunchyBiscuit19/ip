package nemo.gui;

import java.text.MessageFormat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image nemoImage = new Image(this.getClass().getResourceAsStream("/images/nemo.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setStoreLoader(Store store, Loader loader) {
        this.store = store;
        this.loader = loader;
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
                DialogBox.createDukeDialog(nemoText, nemoImage));
        userInput.clear();
    }
}
