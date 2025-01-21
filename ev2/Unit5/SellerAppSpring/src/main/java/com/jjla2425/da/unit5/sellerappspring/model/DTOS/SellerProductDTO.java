package com.jjla2425.da.unit5.sellerappspring.model.DTOS;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.sql.Date;

public class SellerProductDTO
{
    private int sellerProductId;

    private Integer sellerId;

    private Integer productId;
    private String productName;

    private BigDecimal price;

    private BigDecimal offerPrice;

    private Date offerStartDate;

    private Date offerEndDate;

    private int stock;

}
