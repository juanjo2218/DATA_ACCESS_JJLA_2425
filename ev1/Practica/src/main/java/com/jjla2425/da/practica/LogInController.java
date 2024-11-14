package com.jjla2425.da.practica;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LogInController {
    @FXML
    private TextField CIFField;
    @FXML
    private PasswordField passwordField;


    @FXML
    protected void validatePassword()
    {
        String CIF = CIFField.getText();
        String passwordFieldText = passwordField.getText();
        if (CIF == null && passwordFieldText == null)
        {
            Utils.showScreen("Login Error","All fields are required", Alert.AlertType.ERROR);
            return;
        }
        SellersEntity sellerdb = DataBaseManager.getInstance().getSellerByCIF(CIF);
        if (sellerdb != null)
        {
            if (sellerdb.getPassword().equals(Utils.getHash(passwordFieldText).toUpperCase()))
            {
                AddOfferController.setSellerActive(sellerdb);
                Utils.changeView("AddOffer.fxml",CIFField);
            }
            else
            {
                Utils.showScreen("Login Error","Incorrect password", Alert.AlertType.ERROR);
            }
        }
        else
        {
            Utils.showScreen("Login Error","Unregistered user", Alert.AlertType.ERROR);
        }
    }
}