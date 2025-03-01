package arin.ui;

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
import javafx.scene.shape.Circle;

import java.io.IOException;

/**
 * A custom control representing a chat message dialog box.
 * Contains a text message and a profile picture.
 */
public class DialogBox extends HBox {

    /** The label displaying the message text. */
    @FXML
    private Label dialog;

    /** The image view displaying the profile picture. */
    @FXML
    private ImageView displayPicture;

    /**
     * Creates a new dialog box with the specified message and profile image.
     *
     * @param message The message to display.
     * @param img The profile image to show.
     */
    private DialogBox(String message, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(message);
        displayPicture.setImage(img);
        makeProfilePictureCircular();
    }

    /**
     * Makes the profile picture circular by applying a clip and styling.
     */
    private void makeProfilePictureCircular() {
        // Set properties for proper image display
        displayPicture.setPreserveRatio(true);
        displayPicture.setSmooth(true);

        // Create circular clip
        double radius = Math.min(displayPicture.getFitWidth(), displayPicture.getFitHeight()) / 2;
        Circle clip = new Circle(radius);

        // Important: set clip center to match center of the image view
        clip.setCenterX(radius);
        clip.setCenterY(radius);

        // Apply clip and style
        displayPicture.setClip(clip);
        displayPicture.getStyleClass().add("circular-image");
    }

    /**
     * Flips the dialog box layout to show the profile picture on the left
     * and adds reply styling.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Creates a dialog box for user messages.
     *
     * @param message The user's message.
     * @param img The user's profile image.
     * @return A DialogBox configured for user messages.
     */
    public static DialogBox getUserDialog(String message, Image img) {
        DialogBox db = new DialogBox(message, img);
        db.setAlignment(Pos.TOP_RIGHT);
        return db;
    }

    /**
     * Creates a dialog box for Arin's responses.
     *
     * @param message Arin's response message.
     * @param img Arin's profile image.
     * @return A DialogBox configured for Arin's responses.
     */
    public static DialogBox getArinDialog(String message, Image img) {
        DialogBox db = new DialogBox(message, img);
        db.flip();
        return db;
    }

    /**
     * Creates a dialog box for error messages.
     *
     * @param message The error message to display.
     * @param img The profile image to show.
     * @return A DialogBox configured for error messages.
     */
    public static DialogBox getErrorDialog(String message, Image img) {
        DialogBox db = new DialogBox(message, img);
        db.flip();
        db.dialog.getStyleClass().add("error-message");
        return db;
    }
}
