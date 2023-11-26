package sr.frostybee.attraction;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GravitationStage extends Stage {

    private FXMLGravitationController controller;

    public GravitationStage() {
        try {
            // Load the scene graph from FXML layout file. 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GravitationApp_layout.fxml"));
            controller = new FXMLGravitationController();
            loader.setController(controller);
            Pane root = loader.load();
            Scene scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
            setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(GravitationStage.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setOnCloseRequest((event) -> {
            controller.stopSimulation();
        });
    }
}
