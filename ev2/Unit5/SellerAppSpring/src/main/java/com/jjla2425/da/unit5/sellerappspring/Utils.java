package com.jjla2425.da.unit5.sellerappspring;

import com.jjla2425.da.unit5.sellerappspring.model.daos.ISellerProductsDAO;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellerProductsEntity;
import com.jjla2425.da.unit5.sellerappspring.services.SellerProductService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {
    static SellerProductService sellerProductService;
    public static boolean getProductsSellerInThisDate(int sellerId, LocalDate fromDate, LocalDate toDate, int productId) {
        List<SellerProductsEntity> productsSeller = sellerProductService.findAllSellerProductsByIdSellerAndActive(sellerId);
        Date fromDateAsDate = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date toDateAsDate = Date.from(toDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        for (SellerProductsEntity productEntity : productsSeller) {
            if (productEntity.getProductId() == productId) {
                continue;
            }
            Date offerStartDate = productEntity.getOfferStartDate();
            Date offerEndDate = productEntity.getOfferEndDate();

            if (offerStartDate == null && offerEndDate == null) {
                continue;
            }
            if (offerStartDate != null && offerEndDate == null)
            {
                if (!toDateAsDate.before(offerStartDate))
                {
                    return true;
                }
            }
            if (offerStartDate == null && offerEndDate != null) {
                if (!fromDateAsDate.after(offerEndDate)) {
                    return true;
                }
            }
            if (offerStartDate != null && offerEndDate != null) {
                boolean startOverlap = !toDateAsDate.before(offerStartDate);
                boolean endOverlap = !fromDateAsDate.after(offerEndDate);

                if (startOverlap && endOverlap)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
