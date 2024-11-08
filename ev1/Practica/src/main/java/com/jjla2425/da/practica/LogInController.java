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
    private ComboBox<String> categoryComboBox;
    @FXML
    private ComboBox<String> productComboBox;


    public void viewCategories()
    {
        // Obtener las categorías desde la base de datos
        ArrayList<CategoriesEntity> categoriesFromDb = DataBaseManager.getInstance().getCategories();

        // Crear una lista de nombres de categorías (String)
        ArrayList<String> categoryNames = new ArrayList<>();

        // Extraer solo los nombres de las categorías de cada objeto CategoriesEntity
        for (CategoriesEntity category : categoriesFromDb) {
            categoryNames.add(category.getCategoryName());
        }

        // Convertir la lista de nombres a un ObservableList para usar en el ComboBox
        ObservableList<String> observableCategories = FXCollections.observableArrayList(categoryNames);

        // Asignar la lista de nombres al ComboBox
        categoryComboBox.setItems(observableCategories);
    }


    @FXML
    protected void goToSellerMenu()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Stage currentStage = (Stage) CIFField.getScene().getWindow();
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
                goToSellerMenu();
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