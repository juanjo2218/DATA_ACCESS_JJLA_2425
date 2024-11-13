package com.jjla2425.da.practica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class AddOfferController
{
    @FXML
    public void initialize()
    {
        viewProductsSeller();
    }
    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private TextField discountField;
    private static SellersEntity sellerlogin;
    @FXML
    private ComboBox<String> productComboBox;
    public static void setSellerActive(SellersEntity sellerlogin)
    {
        AddOfferController.sellerlogin = sellerlogin;
    }

    @FXML
    protected void validateAndAddOffer()
    {
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = fromDatePicker.getValue();
        int maxOffer =  Utils.getMaxDiscount((int) ChronoUnit.DAYS.between(fromDate,toDate));
        System.out.println("daysDiff: " + (int) ChronoUnit.DAYS.between(fromDate,toDate));  // Imprime el valor para ver si es correcto

        if (Integer.parseInt(discountField.getText()) <= maxOffer)
        {

        }
        else
            Utils.showErrorAlert("Discount Error","El descuento es mayor de lo permitido por esa " +
                    "cantidad de dias(" + maxOffer + ")", Alert.AlertType.ERROR);
    }

    private void viewProductsSeller()
    {
        ArrayList<SellerProductsEntity> productsSeller =
                DataBaseManager.getInstance().getProductsSeller(sellerlogin.getCif());
        ArrayList<ProductsEntity> productsSellername =
                DataBaseManager.getInstance().convertSellerProductsEntityToProductsEntity(productsSeller);
        ArrayList<String> productsRemaningName = new ArrayList<>();
        for (ProductsEntity product : productsSellername) {
            productsRemaningName.add(product.getProductName());
        }
        ObservableList<String> observableCategories = FXCollections.observableArrayList(productsRemaningName);
        productComboBox.setItems(observableCategories);
    }
}
