package com.jjla2425.da.unit5.sellerappspring;

import com.jjla2425.da.unit5.sellerappspring.model.entities.SellerProductsEntity;
import com.jjla2425.da.unit5.sellerappspring.services.SellerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
public class Utils {

    private static SellerProductService sellerProductService;

    // Constructor para inyectar el servicio
    @Autowired
    public Utils(SellerProductService sellerProductService) {
        Utils.sellerProductService = sellerProductService;
    }

    public static boolean getProductsSellerInThisDate(int sellerId, LocalDate fromDate, LocalDate toDate, int productId) {
        List<SellerProductsEntity> productsSeller = sellerProductService.findAllSellerProductsByIdSellerAndActive(sellerId);

        for (SellerProductsEntity productEntity : productsSeller) {
            if (productEntity.getProductId() == productId) {
                continue;  // Skip the current product
            }

            LocalDate offerStartDate = productEntity.getOfferStartDate();
            LocalDate offerEndDate = productEntity.getOfferEndDate();

            // If no offer dates are set, skip the product
            if (offerStartDate == null && offerEndDate == null) {
                continue;
            }

            // If there's no end date, only check if the offer's start date overlaps with the new offer's end date
            if (offerStartDate != null && offerEndDate == null) {
                if (!toDate.isBefore(offerStartDate)) {
                    return true;  // There is an overlap
                }
            }

            // If there's no start date, only check if the offer's end date overlaps with the new offer's start date
            else if (offerStartDate == null) {
                if (!fromDate.isAfter(offerEndDate)) {
                    return true;  // There is an overlap
                }
            }

            // If both start and end dates are present, check for overlapping intervals
            else {
                boolean isStartOverlap = !toDate.isBefore(offerStartDate);
                boolean isEndOverlap = !fromDate.isAfter(offerEndDate);

                if (isStartOverlap && isEndOverlap) {
                    return true;  // There is an overlap
                }
            }
        }

        return false;  // No overlaps found
    }
}
