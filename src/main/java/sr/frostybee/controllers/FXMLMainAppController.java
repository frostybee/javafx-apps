package sr.frostybee.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @FXML
    public void initialize() {
        //-- Add events to the gallery's app launcher buttons. 
        btnMoneyBag.setOnAction((event) -> {
            showStage(new MoneyBagApp());
        });
        btnBouncingBalls.setOnAction((event) -> {
            showStage(new RandomBouncingBalls());
        });
        btnBouncingWithCanvas.setOnAction((event) -> {
            showStage(new BouncingWithCanvas());
        });
        btnBouncingGravity.setOnAction((event) -> {
            showStage(new GravityBalls());
        });

        btnGravitation.setOnAction((event) -> {
                showStage(new GravitationStage());
        });
    }

    private void showStage(Stage aStage) {
        aStage.show();
        AppHelpers.bringToFront(aStage);
    }
}
