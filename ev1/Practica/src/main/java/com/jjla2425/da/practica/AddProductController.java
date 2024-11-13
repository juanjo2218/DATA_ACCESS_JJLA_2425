package com.jjla2425.da.practica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AddProductController
{
    private static SellersEntity sellerlogin;
    @FXML
    private ComboBox<CategoriesEntity> categoryComboBox;
    @FXML
    private ComboBox<ProductsEntity> productComboBox;
    @FXML
    private Slider stockSlider;
    @FXML
    private TextField priceField;

    @FXML
    public void initialize()
    {
        viewCategories();
    }
    public static void setSellerActive(SellersEntity sellerlogin)
    {
        AddProductController.sellerlogin = sellerlogin;
    }
    public void viewCategories() {
        // Obtener las categorías desde la base de datos
        ArrayList<CategoriesEntity> categoriesFromDb = DataBaseManager.getInstance().getCategories();

        // Convertir la lista a un ObservableList para el ComboBox
        ObservableList<CategoriesEntity> observableCategories = FXCollections.observableArrayList(categoriesFromDb);

        // Asignar la lista completa de categorías al ComboBox
        categoryComboBox.setItems(observableCategories);

        // Configurar el cellFactory para mostrar solo el nombre de la categoría en la lista desplegable
        categoryComboBox.setCellFactory(lv -> new ListCell<CategoriesEntity>() {
            @Override
            protected void updateItem(CategoriesEntity item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getCategoryName());
            }
        });

        // Configurar cómo se muestra el elemento seleccionado (solo el nombre)
        categoryComboBox.setButtonCell(new ListCell<CategoriesEntity>() {
            @Override
            protected void updateItem(CategoriesEntity item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getCategoryName());
            }
        });
    }
    public void viewProductsRemaning()
    {
        ArrayList<ProductsEntity> productremaining =
                DataBaseManager.getInstance().getProductsRemainingSellerWithCategoryId(sellerlogin.getCif(),categoryComboBox.getSelectionModel().getSelectedItem().getCategoryId());
        ObservableList<ProductsEntity> observableProducts = FXCollections.observableArrayList(productremaining);
        productComboBox.setItems(observableProducts);
        productComboBox.setCellFactory(lv -> new ListCell<ProductsEntity>() {
            @Override
            protected void updateItem(ProductsEntity item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getProductName());
            }
        });

        productComboBox.setButtonCell(new ListCell<ProductsEntity>() {
            @Override
            protected void updateItem(ProductsEntity item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getProductName());
            }
        });
    }
    public void  AddProduct()
    {
        BigDecimal price = Utils.getPriceAsBigDecimal(priceField.getText());
        ProductsEntity productsEntity =  productComboBox.getSelectionModel().getSelectedItem();
        if (productsEntity == null)
        {
            Utils.showErrorAlert("Error: Product","Choose a product to add.", Alert.AlertType.ERROR);
            return;
        }

        if (price == null)
            Utils.showErrorAlert("Error: Price not valid","Please enter a valid number.", Alert.AlertType.ERROR);
        else
        {
            DataBaseManager.getInstance().addProductsSeller(new SellerProductsEntity(sellerlogin.getSellerId(),productsEntity.getProductId()
                    ,price,(int)stockSlider.getValue()));
            viewProductsRemaning();
            Utils.showErrorAlert("Add product","Product added successfully.", Alert.AlertType.INFORMATION);
        }
    }
}

