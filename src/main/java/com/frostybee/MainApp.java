package com.frostybee;

import com.frostybee.controllers.FXMLMainAppController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This is a JavaFX project template to be used for creating GUI applications.
 * JavaFX 20.0.2 is already linked to this project in the build.gradle file.
 * @link: https://openjfx.io/javadoc/20/
 * @see: Build Scripts/build.gradle
 * @author frostybee.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            //-- Load the scene graph from the specified FXML file and 
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainApp_layout.fxml"));
            //-- Associate the main scene graph with its FXML controller.
            loader.setController(new FXMLMainAppController());
            Pane root = loader.load();
            // Create and set the scene to the stage.
            Scene scene = new Scene(root, 500, 300);
            primaryStage.setScene(scene);
            primaryStage.setTitle("JavaFX Sample Applications Demo");
            primaryStage.sizeToScene();
            primaryStage.setAlwaysOnTop(true);
            primaryStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
