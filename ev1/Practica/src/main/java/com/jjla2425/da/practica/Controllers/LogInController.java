package com.jjla2425.da.practica.Controllers;

import com.jjla2425.da.practica.DataBase.DataBaseManager;
import com.jjla2425.da.practica.model.DataBaseEntities.SellersEntity;
import com.jjla2425.da.practica.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class LogInController {
    @FXML
    private CheckBox rememberCheck;
    @FXML
    private TextField CIFField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void initialize() throws IOException {
        remeberCIF();
    }
    public void remeberCIF() throws IOException {
        CIFField.setText(Utils.getSaveCIF());
    }
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
                if (rememberCheck.selectedProperty().get())
                     Utils.saveCIF(sellerdb.getCif());
                MenuController.setSellerActive(sellerdb);
                Utils.changeView("Menu.fxml",CIFField);
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