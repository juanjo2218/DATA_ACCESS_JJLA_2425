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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Stage currentStage = (Stage) CIFField.getScene().getWindow();
            MenuController.setSellerActive(sellersEntity);
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
        SellersEntity sellerdb = DataBaseManager.getInstance().getSellerByCIF(CIF);
        if (sellerdb != null)
        {
            if (sellerdb.getPassword().equals(Utils.getHash(passwordFieldText).toUpperCase()))
            {
                goToSellerMenu(sellerdb);
            }
            else
            {
                Utils.showErrorAlert("Login Error","Contraseña incorrecta");
            }
        }
        else
        {
            Utils.showErrorAlert("Login Error","Usuario no registrado");
        }
    }
}