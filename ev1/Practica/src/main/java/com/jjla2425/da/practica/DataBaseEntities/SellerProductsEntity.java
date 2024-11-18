package com.jjla2425.da.practica.DataBaseEntities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "seller_products", schema = "public", catalog = "OnlineMarket")
public class SellerProductsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "seller_product_id")
    private int sellerProductId;
    @Basic
    @Column(name = "seller_id")
    private Integer sellerId;
    @Basic
    @Column(name = "product_id")
    private Integer productId;
    @Basic
    @Column(name = "price")
    private BigDecimal price;
    @Basic
    @Column(name = "offer_price")
    private BigDecimal offerPrice;
    @Basic
    @Column(name = "offer_start_date")
    private Date offerStartDate;
    @Basic
    @Column(name = "offer_end_date")
    private Date offerEndDate;
    @Basic
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
