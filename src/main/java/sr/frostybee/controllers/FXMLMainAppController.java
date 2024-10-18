package sr.frostybee.controllers;

import sr.frostybee.bouncing.BouncingWithCanvas;
import sr.frostybee.bouncing.RandomBouncingBalls;
import sr.frostybee.gravityballs.GravityBalls;
import sr.frostybee.moneybag.MoneyBagApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sr.frostybee.attraction.GravitationStage;
import sr.frostybee.common.AppHelpers;

/**
 * FXML controller class for the FX Apps Gallery's main stage.
 *
 * @author frostybee
 */
public class FXMLMainAppController {

    @FXML
    Button btnMoneyBag;
    @FXML
    Button btnBouncingBalls;
    @FXML
    Button btnBouncingWithCanvas;
    @FXML
    Button btnBouncingGravity;
    @FXML
    Button btnGravitation;
    private Stage mainStage;

    @FXML
    public void initialize() {
        //-- Add events to the gallery's app launcher buttons. 
        btnMoneyBag.setOnAction((event) -> {
            showStage(new MoneyBagApp(mainStage));
        });
        btnBouncingBalls.setOnAction((event) -> {
            showStage(new RandomBouncingBalls(mainStage));
        });
        btnBouncingWithCanvas.setOnAction((event) -> {
            showStage(new BouncingWithCanvas(mainStage));
        });
        btnBouncingGravity.setOnAction((event) -> {
            showStage(new GravityBalls(mainStage));
        });

        btnGravitation.setOnAction((event) -> {
                showStage(new GravitationStage(mainStage));
        });
    }

    private void showStage(Stage aStage) {
        aStage.show();
        AppHelpers.bringToFront(aStage);
    }

    public void setStageOwnder(Stage primaryStage) {
        mainStage = primaryStage;
    }
}
