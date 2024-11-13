package com.jjla2425.da.practica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class LogInController {
    @FXML
    private TextField CIFField;
    @FXML
    private PasswordField passwordField;


    @FXML
    protected void goToSellerMenu(SellersEntity sellersEntity)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddOffer.fxml"));
            Stage currentStage = (Stage) CIFField.getScene().getWindow();
            AddOfferController.setSellerActive(sellersEntity);
            Scene nextScene = new Scene(loader.load());
            currentStage.setScene(nextScene);
            currentStage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    protected void validatePassword()
    {
        // Obtener los valores de los campos
        String CIF = CIFField.getText();
        String passwordFieldText = passwordField.getText();
        if (CIF == null && passwordFieldText == null)
        {
            Utils.showErrorAlert("Login Error","All fields are required", Alert.AlertType.ERROR);
            return;
        }
        SellersEntity sellerdb = DataBaseManager.getInstance().getSellerByCIF(CIF);
        if (sellerdb != null)
        {
            if (sellerdb.getPassword().equals(Utils.getHash(passwordFieldText).toUpperCase()))
            {
                goToSellerMenu(sellerdb);
            }
            else
            {
                Utils.showErrorAlert("Login Error","Incorrect password", Alert.AlertType.ERROR);
            }
        }
        else
        {
            Utils.showErrorAlert("Login Error","Unregistered user", Alert.AlertType.ERROR);
        }
    }
}