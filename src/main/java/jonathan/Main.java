package jonathan;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Starts the JavaFX GUI for Jonathan.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource("/view/MainWindow.fxml"));

            AnchorPane mainWindow = loader.load();
            Scene scene = new Scene(mainWindow);

            stage.setTitle("Jonathan");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            throw new RuntimeException(
                    "Unable to load MainWindow.fxml", exception);
        }
    }
}

