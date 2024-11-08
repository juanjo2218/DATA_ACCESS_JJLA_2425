package com.jjla2425.da.practica;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AddDiscountController
{
    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private TextField discountField;
    @FXML
    protected void validateAndAddOffer()
    {
        // Obtener los valores de los campos
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = fromDatePicker.getValue();
        int maxOffer =  Utils.getMaxDiscount((int) ChronoUnit.DAYS.between(fromDate,toDate));
        if (Integer.parseInt(discountField.getText()) > maxOffer)

            DataBaseManager.getInstance().addOfferProductsSeller(new ProductsEntity());
        else
            Utils.showErrorAlert("Discount Error","El descuento es mayor de lo permitido por esa " +
                    "cantidad de dias(" + maxOffer + ")");
    }
}
