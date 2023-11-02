package com.frostybee.controllers;

import com.frostybee.bouncing.BouncingWithCanvas;
import com.frostybee.bouncing.RandomBouncingBalls;
import com.frostybee.gravityballs.GravityBalls;
import com.frostybee.moneybag.MoneyBagApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * FXML controller class for the gallery's main stage.
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
    public void initialize() {

        btnMoneyBag.setOnAction((event) -> {
            MoneyBagApp moneyBag = new MoneyBagApp();
            moneyBag.show();
        });
        btnBouncingBalls.setOnAction((event) -> {
            RandomBouncingBalls bouncingBalls = new RandomBouncingBalls();
            bouncingBalls.show();
        });
        btnBouncingWithCanvas.setOnAction((event) -> {
            BouncingWithCanvas bouncingBalls = new BouncingWithCanvas();
            bouncingBalls.show();
        });
         btnBouncingGravity.setOnAction((event) -> {
            GravityBalls ballsGravity = new GravityBalls();
            ballsGravity.show();
        });
    }
}
