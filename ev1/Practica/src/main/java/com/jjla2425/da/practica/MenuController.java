package com.jjla2425.da.practica;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.stage.Modality;
import java.io.IOException;

public class MenuController
{
    static SellersEntity sellerlogin;

    @FXML
    private TextField businessNameField;

    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private Label nameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private PasswordField passwordFieldMenu;
    @FXML
    private TextField cifField;

    @FXML
    private TextField nameField;

    public static void setSellerActive(SellersEntity sellerlogin)
    {
        MenuController.sellerlogin = sellerlogin;
    }
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
    }
    @FXML
    public void updateDatabase() {
        String cif = cifField.getText();
        String name = nameField.getText();
        String businessName = businessNameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String password = passwordFieldMenu.getText();
        if (name.isEmpty()  || password.isEmpty())
        {
            Utils.showScreen("Error update", "The fields: " + nameLabel.getText() + passwordLabel.getText() +" are mandatory.", Alert.AlertType.ERROR);
            return;
        }
        String passwordHash = Utils.getHash(password);
        if(!Utils.isNumberValid(phone))
        {
            Utils.showScreen("Error phone", "Phone does not support letters ", Alert.AlertType.ERROR);
            return;
        }
        if(!Utils.isEmailValid(email))
        {
            Utils.showScreen("Error email", "Email is not formatted correctly", Alert.AlertType.ERROR);
            return;
        }

        if (!sellerlogin.getPassword().equals(passwordHash.toUpperCase())) {
            ChangePasswordController.setSellerActive(sellerlogin);
            ChangePasswordController passwordChangeController = showPasswordChangeDialog();
            assert passwordChangeController != null;
            if (passwordChangeController.isPasswordChanged()) {
                sellerlogin.setPassword(Utils.getHash(passwordChangeController.getNewPassword()).toUpperCase());
                sellerlogin.setPlainPassword(passwordChangeController.getNewPassword());
            }
        }
        sellerlogin.setBusinessName(businessName);
        sellerlogin.setName(name);
        sellerlogin.setEmail(email);
        sellerlogin.setPhone(phone);
        DataBaseManager.getInstance().updateSeller(sellerlogin);
        Utils.showScreen("Update", "Update done correctly", Alert.AlertType.INFORMATION);
        setSellerDates();
    }

    private ChangePasswordController showPasswordChangeDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jjla2425/da/practica/CambiarContraseña.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Change Password");
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
        @FXML
    protected void goToAddProductMenu()
    {
        AddProductController.setSellerActive(sellerlogin);
        Utils.changeView("AddProduct.fxml",cifField);

    }
    @FXML
    protected void goToAddOfferMenu()
    {
        AddOfferController.setSellerActive(sellerlogin);
        Utils.changeView("AddOffer.fxml",cifField);
    }
}
