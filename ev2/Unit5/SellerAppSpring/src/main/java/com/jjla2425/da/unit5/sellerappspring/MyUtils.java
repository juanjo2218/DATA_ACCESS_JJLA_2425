package com.jjla2425.da.unit5.sellerappspring;

import com.jjla2425.da.unit5.sellerappspring.model.entities.SellerProductsEntity;
import com.jjla2425.da.unit5.sellerappspring.services.SellerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class MyUtils {

    private static SellerProductService sellerProductService;

    // Constructor para inyectar el servicio
    @Autowired
    public MyUtils(SellerProductService sellerProductService) {
        MyUtils.sellerProductService = sellerProductService;
    }

    public static boolean checkOfferDateOverlap(int sellerId, LocalDate fromDate, LocalDate toDate, int productId) {
        if (fromDate == null || toDate == null) {
            return false;
        }
        List<SellerProductsEntity> productsSeller = sellerProductService.findAllSellerProductsByIdSellerAndActive(sellerId);
        for (SellerProductsEntity productEntity : productsSeller) {
            if (productEntity.getProductId() == productId) {
                continue;
            }
            LocalDate offerStartDate = productEntity.getOfferStartDate();
            LocalDate offerEndDate = productEntity.getOfferEndDate();
            if (offerStartDate == null || offerEndDate == null) {
                continue;
            }
            boolean isOverlap = false;
            if (!toDate.isBefore(offerStartDate) && !fromDate.isAfter(offerEndDate)) {
                isOverlap = true;
            }

            if (isOverlap) {
                return true;
            }
        }

        return false;
    }
    public static double getMaxDiscount(int daysDiff) {
        if (daysDiff >= 30) return 20;
        else if (daysDiff >= 3) return 30;
        return 50;
    }
}