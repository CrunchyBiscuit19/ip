package nemo.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's
 * face and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    private static final Color JAVAFX_RED_COLOR = Color.color(1, 0, 0);
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Creates a dialog box representing the user and their input
     *
     * @param text   dialog text
     * @param image  profile image of dialog box
     * @param hasError whether the command output from —nemo has an error
     */
    private DialogBox(String text, Image image, Boolean hasError) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        if (hasError) {
            // @@author CrunchyBiscuit19-reused
            // Reused from https://stackoverflow.com/a/61052739
            // with minor modifications
            dialog.setTextFill(JAVAFX_RED_COLOR);
            // @@author
        }
        displayPicture.setImage(image);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the
     * right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialog box for the user side
     *
     * @param text  the message said by the user
     * @param image the profile image of the user
     * @return dialog box of the user
     */
    public static DialogBox createUserDialog(String text, Image image) {
        return new DialogBox(text, image, false);
    }

    /**
     * Creates a dialog box for nemo side
     *
     * @param text  the message said by nemo
     * @param image the profile image of nemo
     * @param hasError whether the command output from nemo has an error
     * @return dialog box of nemo
     */
    public static DialogBox createNemoDialog(String text, Image image, Boolean hasError) {
        var db = new DialogBox(text, image, hasError);
        db.flip();
        return db;
    }
}
