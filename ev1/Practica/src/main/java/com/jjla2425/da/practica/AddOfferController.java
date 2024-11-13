package com.jjla2425.da.practica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.sql.Date;
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
    private ComboBox<ProductsEntity> productComboBox;
    public static void setSellerActive(SellersEntity sellerlogin)
    {
        AddOfferController.sellerlogin = sellerlogin;
    }

    @FXML
    protected void validateAndAddOffer()
    {
        SellerProductsEntity sellerProduct = DataBaseManager.getInstance().getProductSeller(sellerlogin.getCif(),
                productComboBox.getSelectionModel().getSelectedItem().getProductId());
        if (sellerProduct == null)
        {
            Utils.showErrorAlert("AddOffer","Choose a product", Alert.AlertType.ERROR);
            return;
        }
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();
        if (toDate == null || fromDate == null)
        {
            Utils.showErrorAlert("Error","Dates can not be null", Alert.AlertType.ERROR);
            return;
        }
        int daysDiff = (int) ChronoUnit.DAYS.between(fromDate,toDate);
        if (daysDiff < 0 )
        {
            Utils.showErrorAlert("Error","FromDate can not be higher than toDate", Alert.AlertType.ERROR);
            return;
        }
        int maxOffer =  Utils.getMaxDiscount(daysDiff);
        if (Integer.parseInt(discountField.getText()) <= maxOffer)
        {
            if (!DataBaseManager.getInstance().getProductsSellerInThisDate(sellerlogin.getCif(), fromDate, toDate))
            {
                sellerProduct.setOfferStartDate(Date.valueOf(fromDate));
                sellerProduct.setOfferEndDate(Date.valueOf(toDate));
                BigDecimal discountFraction = new BigDecimal(discountField.getText()).divide(new BigDecimal(100));
                BigDecimal offerPrice = sellerProduct.getPrice().subtract(sellerProduct.getPrice().multiply(discountFraction)).setScale(2, BigDecimal.ROUND_HALF_UP);
                sellerProduct.setOfferPrice(offerPrice);
                DataBaseManager.getInstance().addOfferProductsSeller(sellerProduct);
                Utils.showErrorAlert("AddOffer","Offer add correct.Price before:" + sellerProduct.getPrice() +
                        "price after:" + sellerProduct.getOfferPrice(), Alert.AlertType.INFORMATION);
                viewProductsSeller();
            }
            else
                Utils.showErrorAlert("Discount Error","There is already an offer on these dates", Alert.AlertType.ERROR);
        }
        else
            Utils.showErrorAlert("Discount Error","The discount is greater than what is allowed by that " +
                    "number of days(max: " + maxOffer + ")", Alert.AlertType.ERROR);
    }

    private void viewProductsSeller()
    {
        ArrayList<SellerProductsEntity> productsSeller =
                DataBaseManager.getInstance().getProductsSellerNotOfferYet(sellerlogin.getCif());
        ArrayList<ProductsEntity> productsSellername =
                DataBaseManager.getInstance().convertSellerProductsEntityToProductsEntity(productsSeller);
        ObservableList<ProductsEntity> observableProducts =  FXCollections.observableArrayList(productsSellername);;
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
}
