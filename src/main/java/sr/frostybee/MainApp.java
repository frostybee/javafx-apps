package sr.frostybee;

import sr.frostybee.controllers.FXMLMainAppController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sr.frostybee.common.AppHelpers;

/**
 * Application class for the JavaFX samples gallery.
 *
 * @author frostybee.
 */
public class MainApp extends Application {

    private FXMLMainAppController mainAppController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            //-- Load the scene graph from the specified FXML file and 

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainApp_layout.fxml"));
            //-- Associate the main scene graph with its FXML controller.
            mainAppController = new FXMLMainAppController();
            mainAppController.setStageOwnder(primaryStage);
            loader.setController(mainAppController);
            Pane root = loader.load();
            // Create and set the scene to the stage.
            Scene scene = new Scene(root, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.setTitle("JavaFX Applications Gallery");
            primaryStage.sizeToScene();
            primaryStage.show();
            AppHelpers.bringToFront(primaryStage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
