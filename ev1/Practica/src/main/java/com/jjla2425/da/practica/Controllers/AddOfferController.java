package com.jjla2425.da.practica.Controllers;

import com.jjla2425.da.practica.*;
import com.jjla2425.da.practica.DataBase.DataBaseManager;
import com.jjla2425.da.practica.model.DataBaseEntities.ProductsEntity;
import com.jjla2425.da.practica.model.DataBaseEntities.SellerProductsEntity;
import com.jjla2425.da.practica.model.DataBaseEntities.SellersEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.math.RoundingMode;
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
        ProductsEntity productSelected = productComboBox.getSelectionModel().getSelectedItem();
        if (productSelected == null)
        {
            Utils.showScreen("AddOffer","Choose a product", Alert.AlertType.ERROR);
            return;
        }
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();
        if (toDate == null || fromDate == null)
        {
            Utils.showScreen("Error","Dates can not be null", Alert.AlertType.ERROR);
            return;
        }
        if (toDate.isBefore(LocalDate.now()) || toDate.isBefore(LocalDate.now()))
        {
            Utils.showScreen("Error","Dates can not be older than the actual date", Alert.AlertType.ERROR);
            return;
        }
        int daysDiff = (int) ChronoUnit.DAYS.between(fromDate,toDate);
        if (daysDiff < 0 )
        {
            Utils.showScreen("Error","FromDate can not be higher than toDate", Alert.AlertType.ERROR);
            return;
        }
        if (daysDiff > 30 )
        {
            Utils.showScreen("Error","The number of days of the offer cannot be more than 30 days", Alert.AlertType.ERROR);
            return;
        }
        BigDecimal discount = Utils.getPriceAsBigDecimal(discountField.getText());
        if (discount == null ||discount.compareTo(new BigDecimal(0)) <= 0) {
            Utils.showScreen("Error", "Discount can only be a number and a postive number and higher than 0", Alert.AlertType.ERROR);
            return;
        }
        int maxOffer = Utils.getMaxDiscount(daysDiff,sellerlogin.isPro());
        BigDecimal maxOfferAsBigDecimal = new BigDecimal(maxOffer);
        if (discount.compareTo(maxOfferAsBigDecimal) <= 0)
        {
            if (!DataBaseManager.getInstance().getProductsSellerInThisDate(sellerlogin.getSellerId(), fromDate, toDate,productSelected.getProductId(),sellerlogin.isPro()))
            {
                SellerProductsEntity sellerProduct = DataBaseManager.getInstance().getProductSeller(sellerlogin.getCif(),productSelected.getProductId());
                sellerProduct.setOfferStartDate(Date.valueOf(fromDate));
                sellerProduct.setOfferEndDate(Date.valueOf(toDate));
                BigDecimal discountFraction = discount.divide(new BigDecimal(100), 4, RoundingMode.HALF_UP);
                BigDecimal offerPrice = sellerProduct.getPrice()
                        .subtract(sellerProduct.getPrice().multiply(discountFraction))
                        .setScale(2, RoundingMode.HALF_UP);
                sellerProduct.setOfferPrice(offerPrice);
                boolean updated = DataBaseManager.getInstance().addOfferProductsSeller(sellerProduct);
                if (updated)
                    Utils.showScreen("AddOffer","Offer add correct.Price before:" + sellerProduct.getPrice() +
                        " price after:" + sellerProduct.getOfferPrice(), Alert.AlertType.INFORMATION);
                viewProductsSeller();
            }
            else
                Utils.showScreen("Discount Error","There is already an offer on these dates", Alert.AlertType.ERROR);
        }
        else
            Utils.showScreen("Discount Error","The discount is greater than what is allowed by that " +
                    "number of days(max: " + maxOffer + ")", Alert.AlertType.ERROR);
    }
    private void viewProductsSeller()
    {
        ArrayList<SellerProductsEntity> productsSeller =
                DataBaseManager.getInstance().getProductsSellerActive(sellerlogin.getSellerId());
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
    @FXML
    protected void goToAddProductMenu()
    {
        AddProductController.setSellerActive(sellerlogin);
        Utils.changeView("AddProduct.fxml",discountField);

    }
    @FXML
    protected void goToMenu()
    {
        MenuController.setSellerActive(sellerlogin);
        Utils.changeView("Menu.fxml",discountField);
    }
    @FXML
    protected void goToLogin()
    {
        Utils.changeView("InicioSesion.fxml",discountField);
    }

}
