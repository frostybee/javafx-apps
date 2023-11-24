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
        addOnActionEvent(btnMoneyBag, new MoneyBagApp());
        addOnActionEvent(btnBouncingBalls, new RandomBouncingBalls());
        addOnActionEvent(btnBouncingWithCanvas, new BouncingWithCanvas());
        addOnActionEvent(btnBouncingGravity, new GravityBalls());
        //addOnActionEvent(btnGravitation, new GravitationStage());
        btnGravitation.setOnAction((event) -> {
            GravitationStage aStage;
            try {
                aStage = new GravitationStage();
                aStage.show();     
                AppHelpers.bringToFront(aStage);
            } catch (IOException ex) {
                Logger.getLogger(FXMLMainAppController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void addOnActionEvent(Button aButton, Stage aStage) {
        aButton.setOnAction((event) -> {
            aStage.show();
            AppHelpers.bringToFront(aStage);
        });
    }
}
