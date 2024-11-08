package com.jjla2425.da.practica;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController
{

    @FXML
    private TextField businessNameField;

    @FXML
    private TextField phoneField;
    @FXML
    private TextField discountField;

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordFieldMenu;
    @FXML
    private TextField cifField;

    @FXML
    private TextField nameField;

    @FXML
    public void initialize()
    {
        setSellerDates();
    }
    public void setSellerDates() {
        cifField.setText(sellerlogin.getCif());
        nameField.setText(sellerlogin.getName());
        businessNameField.setText(sellerlogin.getBusinessName());
        phoneField.setText(sellerlogin.getPhone());
        emailField.setText(sellerlogin.getEmail());
        passwordFieldMenu.setText(sellerlogin.getPlainPassword());
    }

    @FXML
    protected void goToAddProductMenu()
    {
        changeMenu("AddProduct.fxml",cifField);

    }
    @FXML
    protected void goToAddOfferMenu() {
        changeMenu("AddOffer.fxml",cifField);
    }
    protected void changeMenu(String nameFXML, TextField field) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nameFXML));
            Stage currentStage = (Stage) field.getScene().getWindow();
            Scene nextScene = new Scene(loader.load());
            currentStage.setScene(nextScene); // Cambiar la escena actual
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
