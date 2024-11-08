package com.jjla2425.da.practica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;

public class AddProductController
{
    private static SellersEntity sellerlogin;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private ComboBox<String> productComboBox;

    @FXML
    public void initialize()
    {
        viewCategories();
    }
    public static void setSellerActive(SellersEntity sellerlogin)
    {
        AddProductController.sellerlogin = sellerlogin;
    }
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

    public void viewProductsRemaning()
    {
        String category = String.valueOf(DataBaseManager.getInstance().getCategorieEntityByName(categoryComboBox.getSelectionModel().getSelectedItem()).getCategoryId());
        ArrayList<ProductsEntity> productremaining =
                DataBaseManager.getInstance().getProductsRemainingSellerWithCategory(sellerlogin.getCif(),category);

        // Crear una lista de nombres de categorías (String)
        ArrayList<String> productsRemaningName = new ArrayList<>();

        // Extraer solo los nombres de las categorías de cada objeto CategoriesEntity
        for (ProductsEntity product : productremaining) {
            productsRemaningName.add(product.getProductName());
        }

        // Convertir la lista de nombres a un ObservableList para usar en el ComboBox
        ObservableList<String> observableCategories = FXCollections.observableArrayList(productsRemaningName);

        // Asignar la lista de nombres al ComboBox
        productComboBox.setItems(observableCategories);
    }
}
