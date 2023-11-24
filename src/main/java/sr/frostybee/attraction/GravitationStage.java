package sr.frostybee.attraction;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GravitationStage extends Stage {

    private FXMLGravitationController controller;

    public GravitationStage() throws IOException {
        // Load the scene graph from FXML layout file. 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GravitationApp_layout.fxml"));
        controller = new FXMLGravitationController();
        loader.setController(controller);
        Pane root = loader.load();
        Scene scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        setScene(scene);
        this.setOnCloseRequest((event) -> {
            controller.stopSimulation();
        });
    }
}
