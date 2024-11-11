package com.jjla2425.da.practica;

import com.jjla2425.da.practica.SellersEntity;
import com.jjla2425.da.practica.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;


public class ChangePasswordController
{
    static SellersEntity sellerlogin;

    @FXML
    private PasswordField oldPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;

    private boolean passwordChanged = false;

    public static void setSellerActive(SellersEntity sellerlogin)
    {
        ChangePasswordController.sellerlogin = sellerlogin;
    }

    @FXML
    private void handlePasswordChange()
    {
        String oldPassword = oldPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validar contraseñas
        if (!newPassword.equals(confirmPassword)) {
            Utils.showErrorAlert("Error", "La nueva contraseña y la confirmación no coinciden.");
            return;
        }
        // Aquí deberías validar la contraseña antigua usando Utils
        if (!Utils.getHash(oldPassword).toUpperCase().equals(sellerlogin.getPassword())) {
            Utils.showErrorAlert("Error", "La contraseña antigua es incorrecta.");
            return;
        }

        // Marca como cambiada y cierra la ventana
        passwordChanged = true;
        closeWindow();
    }


    private void closeWindow() {
        Stage stage = (Stage) oldPasswordField.getScene().getWindow();
        stage.close();
    }

    public boolean isPasswordChanged() {
        return passwordChanged;
    }

    public String getNewPassword() {
        return newPasswordField.getText();
    }
}
