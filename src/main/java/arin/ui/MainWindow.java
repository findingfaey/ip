package arin.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller class for the main window of the Arin chatbot.
 * Manages user interactions and displays chat messages.
 */
public class MainWindow extends AnchorPane {

    /** The scroll pane that contains the chat messages. */
    @FXML
    private ScrollPane scrollPane;

    /** The container for all chat dialog boxes. */
    @FXML
    private VBox dialogContainer;

    /** The text field for user input. */
    @FXML
    private TextField userInput;

    /** The button to send messages. */
    @FXML
    private Button sendButton;

    /** The application icon in the top bar. */
    @FXML
    private ImageView appIcon;

    /** The chatbot instance. */
    private Arin arin;

    /** The user's profile image. */
    private Image userImage;

    /** Arin's profile image. */
    private Image arinImage;

    /**
     * Initializes the UI components.
     * This method is automatically called by JavaFX after FXML loading.
     */
    @FXML
    public void initialize() {
        // Configure app icon
        appIcon.setFitHeight(24.0);
        appIcon.setFitWidth(24.0);

        // Load profile images
        try {
            userImage = new Image(getClass().getResourceAsStream("/images/user.jpeg"));
            arinImage = new Image(getClass().getResourceAsStream("/images/arin.jpeg"));

            // Log any image loading errors
            if (userImage.isError()) {
                System.out.println("Error loading user image");
            }
            if (arinImage.isError()) {
                System.out.println("Error loading arin image");
            }
        } catch (Exception e) {
            System.out.println("Error loading profile images: " + e.getMessage());
        }

        // Set up auto-scrolling
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Configure input field
        userInput.setPromptText("Type here...");

        // Set up send button
        try {
            ImageView sendIcon = new ImageView(new Image(
                    getClass().getResourceAsStream("/images/send-icon.png")));
            sendIcon.setFitHeight(24);
            sendIcon.setFitWidth(24);
            sendButton.setGraphic(sendIcon);
            sendButton.setText("");
            sendButton.setPrefSize(40, 40);
        } catch (Exception e) {
            // Fallback to text button if icon loading fails
            sendButton.setText("Send");
        }
    }

    /**
     * Sets the Arin chatbot instance for this window.
     *
     * @param arin The Arin chatbot instance to use.
     */
    public void setArin(Arin arin) {
        this.arin = arin;
    }

    /**
     * Displays Arin's welcome message.
     */
    public void displayWelcomeMessage() {
        String welcomeMessage = arin.getWelcomeMessage();
        DialogBox welcomeBox = DialogBox.getArinDialog(welcomeMessage, arinImage);
        dialogContainer.getChildren().add(welcomeBox);
    }

    /**
     * Handles user input when the send button is clicked or Enter is pressed.
     * Gets response from Arin and displays both user input and response.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.trim().isEmpty()) {
            return;
        }

        // Add user message to dialog
        DialogBox userDialog = DialogBox.getUserDialog(input, userImage);
        dialogContainer.getChildren().add(userDialog);

        // Get and display Arin's response
        String response = arin.getResponse(input);
        DialogBox arinDialog;

        if (response.startsWith("Error:")) {
            arinDialog = DialogBox.getErrorDialog(response, arinImage);
        } else {
            arinDialog = DialogBox.getArinDialog(response, arinImage);
        }

        dialogContainer.getChildren().add(arinDialog);
        userInput.clear();
    }
}
