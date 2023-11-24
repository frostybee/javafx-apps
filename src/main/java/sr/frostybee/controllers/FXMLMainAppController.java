package sr.frostybee.controllers;

import sr.frostybee.bouncing.BouncingWithCanvas;
import sr.frostybee.bouncing.RandomBouncingBalls;
import sr.frostybee.gravityballs.GravityBalls;
import sr.frostybee.moneybag.MoneyBagApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sr.frostybee.attraction.GravitationMain;
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
        addOnActionEvent(btnMoneyBag, new MoneyBagApp());
        addOnActionEvent(btnBouncingBalls, new RandomBouncingBalls());
        addOnActionEvent(btnBouncingWithCanvas, new BouncingWithCanvas());
        addOnActionEvent(btnBouncingGravity, new GravityBalls());
        addOnActionEvent(btnGravitation, new GravitationMain());
    }

    private void addOnActionEvent(Button aButton, Stage aStage) {
        aButton.setOnAction((event) -> {
            aStage.show();
            AppHelpers.bringToFront(aStage);
        });
    }
}
