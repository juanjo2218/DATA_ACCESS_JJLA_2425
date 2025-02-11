package com.jjla2425.da.unit5.sellerappspring;

import com.jjla2425.da.unit5.sellerappspring.model.daos.ISellerProductsDAO;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellerProductsEntity;
import com.jjla2425.da.unit5.sellerappspring.services.SellerProductService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utils {
    static SellerProductService sellerProductService;
    public static boolean getProductsSellerInThisDate(int sellerId, LocalDate fromDate, LocalDate toDate, int productId) {
        List<SellerProductsEntity> productsSeller = sellerProductService.findAllSellerProductsByIdSellerAndActive(sellerId);
        LocalDate fromDateAsDate = LocalDate.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        LocalDate toDateAsDate = LocalDate.from(toDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        for (SellerProductsEntity productEntity : productsSeller) {
            if (productEntity.getProductId() == productId) {
                continue;
            }
            LocalDate offerStartDate = productEntity.getOfferStartDate();
            LocalDate offerEndDate = productEntity.getOfferEndDate();

            if (offerStartDate == null && offerEndDate == null) {
                continue;
            }
            if (offerStartDate != null && offerEndDate == null)
            {
                if (!toDateAsDate.isBefore(offerStartDate))
                {
                    return true;
                }
            }
            if (offerStartDate == null && offerEndDate != null) {
                if (!fromDateAsDate.isAfter(offerEndDate)) {
                    return true;
                }
            }
            if (offerStartDate != null && offerEndDate != null) {
                boolean startOverlap = !toDateAsDate.isBefore(offerStartDate);
                boolean endOverlap = !fromDateAsDate.isAfter(offerEndDate);

                if (startOverlap && endOverlap)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
