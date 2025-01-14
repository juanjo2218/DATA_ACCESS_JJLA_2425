package com.jjla2425.da.unit5.sellerappspring.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Entity
@NamedNativeQuery(name = "SellerProductsEntity.getSellerProductsBySellerIdActives",
        query = "SELECT * from jjla_get_sellerproductsactive(:sellerId)",
        resultClass = SellerProductsEntity.class)
@Table(name = "seller_products", schema = "public", catalog = "OnlineMarket")
public class SellerProductsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @NotNull
    @Column(name = "seller_product_id")
    private int sellerProductId;
    @Basic
    @Column(name = "seller_id")
    private Integer sellerId;
    @Basic
    @Column(name = "product_id")
    private Integer productId;
    @Basic
    @NotNull
    @Digits(integer = 8, fraction = 2, message = "The price must have up to 8 digits before the decimal point and 2 digits after it.")
    @Min(value = 0, message = "The price must be greater than or equal to 0.")
    @Max(value = 99999999, message = "The price must be less than or equal to 99,999,999.99.")
    @Column(name = "price")
    private BigDecimal price;
    @Basic
    @Digits(integer = 8, fraction = 2, message = "The offer price must have up to 8 digits before the decimal point and 2 digits after it.")
    @Min(value = 0, message = "The offer price must be greater than or equal to 0.")
    @Max(value = 99999999, message = "The offer price must be less than or equal to 99,999,999.99.")
    @Column(name = "offer_price")
    private BigDecimal offerPrice;
    @Basic
    @Column(name = "offer_start_date")
    private Date offerStartDate;
    @Basic
    @Column(name = "offer_end_date")
    private Date offerEndDate;
    @Basic
    @NotNull
    @Column(name = "stock")
    private int stock;

    public SellerProductsEntity(Integer sellerId,Integer productId,BigDecimal price,int stock)
    {
        this.sellerId = sellerId;
        this.productId = productId;
        this.price = price;
        this.stock = stock;
    }
    public SellerProductsEntity()
    {}

    public int getSellerProductId() {
        return sellerProductId;
    }

    public void setSellerProductId(int sellerProductId) {
        this.sellerProductId = sellerProductId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(BigDecimal offerPrice) {
        this.offerPrice = offerPrice;
    }

    public Date getOfferStartDate() {
        return offerStartDate;
    }

    public void setOfferStartDate(Date offerStartDate) {
        this.offerStartDate = offerStartDate;
    }

    public Date getOfferEndDate() {
        return offerEndDate;
    }

    public void setOfferEndDate(Date offerEndDate) {
        this.offerEndDate = offerEndDate;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellerProductsEntity that = (SellerProductsEntity) o;
        return sellerProductId == that.sellerProductId && stock == that.stock && Objects.equals(sellerId, that.sellerId) && Objects.equals(productId, that.productId) && Objects.equals(price, that.price) && Objects.equals(offerPrice, that.offerPrice) && Objects.equals(offerStartDate, that.offerStartDate) && Objects.equals(offerEndDate, that.offerEndDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellerProductId, sellerId, productId, price, offerPrice, offerStartDate, offerEndDate, stock);
    }
}
