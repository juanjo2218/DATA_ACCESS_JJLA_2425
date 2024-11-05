package com.jjla2425.da.practica;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "order_details", schema = "public", catalog = "OnlineMarket")
public class OrderDetailsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_detail_id")
    private int orderDetailId;
    @Basic
    @Column(name = "order_id")
    private Integer orderId;
    @Basic
    @Column(name = "seller_product_id")
    private Integer sellerProductId;
    @Basic
    @Column(name = "quantity")
    private int quantity;
    @Basic
    @Column(name = "subtotal_price")
    private BigDecimal subtotalPrice;

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getSellerProductId() {
        return sellerProductId;
    }

    public void setSellerProductId(Integer sellerProductId) {
        this.sellerProductId = sellerProductId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotalPrice() {
        return subtotalPrice;
    }

    public void setSubtotalPrice(BigDecimal subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailsEntity that = (OrderDetailsEntity) o;
        return orderDetailId == that.orderDetailId && quantity == that.quantity && Objects.equals(orderId, that.orderId) && Objects.equals(sellerProductId, that.sellerProductId) && Objects.equals(subtotalPrice, that.subtotalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDetailId, orderId, sellerProductId, quantity, subtotalPrice);
    }
}
