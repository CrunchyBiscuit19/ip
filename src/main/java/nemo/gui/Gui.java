package nemo.gui;

import java.text.MessageFormat;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nemo.command.Command;
import nemo.store.Loader;
import nemo.store.Store;

/**
 * Responsible for running GUI above Nemo.
 */
public class Gui extends Application {
    private Store store;
    private Loader loader;

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image nemoImage = new Image(this.getClass().getResourceAsStream("/images/nemo.png"));

    @Override
    public void start(Stage stage) {
        List<String> args = getParameters().getRaw();
        store = new Store();
        loader = new Loader(args.get(0));
        loader.load(store);

        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.setTitle("Nemo");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);
        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        stage.show();

        sendButton.setOnMouseClicked((event) -> {
            processInput(userInput.getText());
        });
        userInput.setOnAction((event) -> {
            processInput(userInput.getText());
        });

        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

    /**
     * Parse command and arguments, execute command, then write
     * back results into dialog box.
     *
     * @param input the raw input line from the user
     */
    private void processInput(String input) {
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
                new DialogBox(input, userImage),
                new DialogBox(nemoText, nemoImage));
        userInput.clear();
    }
}
