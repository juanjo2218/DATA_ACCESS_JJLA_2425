package com.jjla2425.da.practica;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    private TextField usernameField;
    private SellersEntity sellerlogin;

    @FXML
    private PasswordField passwordField;
    @FXML
    protected void validatePassword()
    {
        // Obtener los valores de los campos
        String CIF = usernameField.getText();
        String passwordFieldText = passwordField.getText();
        SellersEntity sellerdb = DataBaseManager.getInstance().getSellerByCIF(CIF);
        if (sellerdb != null)
        {
            if (sellerdb.getPassword().equals(Utils.getHash(passwordFieldText)))
            {
                try {
                    sellerlogin = sellerdb;
                    // Cargar la nueva escena (pantalla) desde un archivo FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
                    Stage currentStage = (Stage) usernameField.getScene().getWindow();
                    Scene nextScene = new Scene(loader.load());
                    currentStage.setScene(nextScene); // Cambiar la escena actual
                    currentStage.show();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                //mostrar error de que contraseña incorrecta
            }
        }
        else
        {
            //mostrar error de que ese usario no existe
        }

    }
}