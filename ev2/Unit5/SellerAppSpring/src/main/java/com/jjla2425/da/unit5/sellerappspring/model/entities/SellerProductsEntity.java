package com.jjla2425.da.unit5.sellerappspring.model.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;

@Entity
@NamedNativeQuery(name = "SellerProductsEntity.getSellerProductsBySellerIdActives",
        query = "SELECT * from jjla_get_sellerproductsactive(:sellerId)",
        resultClass = SellerProductsEntity.class)
@Table(name = "seller_products", schema = "public", catalog = "OnlineMarket")
public class SellerProductsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @NotNull(message = "SellerProduct can not be null,please choose a product")
    @Column(name = "seller_product_id")
    private int sellerProductId;
    @Basic
    @Column(name = "seller_id")
    private Integer sellerId;
    @Basic
    @Column(name = "product_id")
    private Integer productId;
    @Basic
    @NotNull(message = "Price can not be null")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate offerStartDate;

    @Basic
    @Column(name = "offer_end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate offerEndDate;

    @Basic
    @NotNull(message = "Stock can not be null")
    @Column(name = "stock")
    private int stock;

    @AssertTrue(message = "Product can not be null, please choose a product")
    public boolean isProductIdValid() {
        return productId != null;
    }
    @AssertTrue(message = "Offer start date and end date can not be null or the difference cannot be more than 30 days.")
    public boolean isOfferDatesNotNulls() {
        if (sellerId == null || productId == null)
            return false;
        if (offerStartDate == null || offerEndDate == null) {
            return false;
        }

        return ChronoUnit.DAYS.between(offerStartDate, offerEndDate) < 30;
    }
    public SellerProductsEntity(Integer sellerId)
    {
        this.sellerId = sellerId;
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

    public LocalDate getOfferStartDate() {
        return offerStartDate;
    }

    public void setOfferStartDate(LocalDate offerStartDate) {
        this.offerStartDate = offerStartDate;
    }

    public LocalDate getOfferEndDate() {
        return offerEndDate;
    }

    public void setOfferEndDate(LocalDate offerEndDate) {
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
