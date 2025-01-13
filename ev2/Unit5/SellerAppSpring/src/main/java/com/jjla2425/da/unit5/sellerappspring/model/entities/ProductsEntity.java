package com.jjla2425.da.unit5.sellerappspring.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@NamedNativeQuery(name = "ProductsEntity.getProductsByCategoryAndSellerIdRemaning",
        query = "SELECT jjla_getproductssellerremaining( :p_idseller, :p_idcategory)",
        resultClass = ProductsEntity.class)
@Table(name = "products", schema = "public", catalog = "OnlineMarket")
public class ProductsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @NotNull(message = "Name can not be null")
    @Column(name = "product_id")
    private int productId;
    @Basic
    @Size(min = 2,max = 100,message = "Name size must be between 2 and 100")
    @NotEmpty(message = "Name can empty")
    @NotNull(message = "Name can not be null")
    @Column(name = "product_name")
    private String productName;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "category_id")
    private Integer categoryId;
    @Basic
    @NotNull(message = "Name can not be null")
    @Column(name = "active")
    private boolean active;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductsEntity that = (ProductsEntity) o;
        return productId == that.productId && active == that.active && Objects.equals(productName, that.productName) && Objects.equals(description, that.description) && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, description, categoryId, active);
    }
}
