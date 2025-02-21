package arin.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main entry point for the JavaFX UI.
 */
public class Main extends Application {

    private Arin arin;

    @Override
    public void init() {
        this.arin = new Arin("./data/arin.txt");
        this.arin.setGuiMode();
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane root = fxmlLoader.load();
            Scene scene = new Scene(root);

            // Add CSS
            scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/css/dialog-box.css").toExternalForm());

            // Configure window
            stage.setTitle("Arin");
            stage.setResizable(true);
            stage.setMinHeight(600.0);
            stage.setMinWidth(400.0);

            stage.setScene(scene);

            // Set up the controller
            MainWindow controller = fxmlLoader.getController();
            controller.setArin(arin);
            controller.displayWelcomeMessage();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}