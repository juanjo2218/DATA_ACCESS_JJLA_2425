package com.jjla2425.da.practica.Controllers;

import com.jjla2425.da.practica.*;
import com.jjla2425.da.practica.DataBase.DataBaseManager;
import com.jjla2425.da.practica.model.DataBaseEntities.CategoriesEntity;
import com.jjla2425.da.practica.model.DataBaseEntities.ProductsEntity;
import com.jjla2425.da.practica.model.DataBaseEntities.SellerProductsEntity;
import com.jjla2425.da.practica.model.DataBaseEntities.SellersEntity;
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
        ArrayList<CategoriesEntity> categoriesFromDb = DataBaseManager.getInstance().getCategories();

        ObservableList<CategoriesEntity> observableCategories = FXCollections.observableArrayList(categoriesFromDb);

        categoryComboBox.setItems(observableCategories);

        categoryComboBox.setCellFactory(lv -> new ListCell<CategoriesEntity>() {
            @Override
            protected void updateItem(CategoriesEntity item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getCategoryName());
            }
        });

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
                DataBaseManager.getInstance().getProductsRemainingSellerWithCategoryId(sellerlogin.getSellerId(),categoryComboBox.getSelectionModel().getSelectedItem().getCategoryId());
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
            Utils.showScreen("Error: Product","Choose a product to add.", Alert.AlertType.ERROR);
            return;
        }

        if (price == null)
        {
            Utils.showScreen("Error: Price not valid","Please enter a number.", Alert.AlertType.ERROR);
            return;
        }
        BigDecimal MAX_VALUE = new BigDecimal("99999999.99");
        BigDecimal minValue = new BigDecimal("0");
        if (price.compareTo(MAX_VALUE) > 0 || price.compareTo(minValue) < 0)
            Utils.showScreen("Error: Price not valid","Please enter a higher than 0 and lower than 99999999.99.", Alert.AlertType.ERROR);
        else
        {
            boolean inserted =DataBaseManager.getInstance().addProductsSeller(new SellerProductsEntity(sellerlogin.getSellerId(),productsEntity.getProductId()
                    ,price,(int)stockSlider.getValue()));
            viewProductsRemaning();
            if (inserted)
                Utils.showScreen("Add product","Product added successfully.", Alert.AlertType.INFORMATION);
        }
    }
    @FXML
    protected void goToAddProductMenu()
    {
        AddProductController.setSellerActive(sellerlogin);
        Utils.changeView("AddProduct.fxml",priceField);

    }
    @FXML
    protected void goToAddOfferMenu()
    {
        AddOfferController.setSellerActive(sellerlogin);
        Utils.changeView("AddOffer.fxml",priceField);
    }
    @FXML
    protected void goToMenu()
    {
        MenuController.setSellerActive(sellerlogin);
        Utils.changeView("Menu.fxml",priceField);
    }
    @FXML
    protected void goToLogin()
    {
        Utils.changeView("InicioSesion.fxml",priceField);
    }
}

