package com.frostybee.controllers;

import com.frostybee.moneybag.MoneyBagApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controller class of the MainApp's UI.
 *
 * @author frostybee
 */
public class FXMLMainAppController {

    @FXML
    Button btnMoneyBag;

    @FXML
    public void initialize() {

        btnMoneyBag.setOnAction((event) -> {
            MoneyBagApp moneyBag = new MoneyBagApp();
            moneyBag.show();
        });
    }

}
