package com.jjla2425.da.practica;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "sellers", schema = "public", catalog = "OnlineMarket")
public class SellersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "seller_id")
    private int sellerId;
    @Basic
    @Column(name = "cif")
    private String cif;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "business_name")
    private String businessName;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "plain_password")
    private String plainPassword;
    @Basic
    @Column(name = "password")
    private String password;

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellersEntity that = (SellersEntity) o;
        return sellerId == that.sellerId && Objects.equals(cif, that.cif) && Objects.equals(name, that.name) && Objects.equals(businessName, that.businessName) && Objects.equals(phone, that.phone) && Objects.equals(email, that.email) && Objects.equals(plainPassword, that.plainPassword) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellerId, cif, name, businessName, phone, email, plainPassword, password);
    }
}
