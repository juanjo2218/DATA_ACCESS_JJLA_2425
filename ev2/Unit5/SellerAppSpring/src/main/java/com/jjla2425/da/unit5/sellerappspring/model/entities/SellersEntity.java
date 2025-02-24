package com.jjla2425.da.unit5.sellerappspring.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.util.Objects;

@Entity
@Table(name = "sellers", schema = "public", catalog = "OnlineMarket")
public class SellersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @NotNull(message = "Name can not be null")
    @Column(name = "seller_id")
    private int sellerId;
    @Basic
    @Column(name = "cif")
    @Size(min = 2,max = 20,message = "CIF size must be between 2 and 20")
    @NotNull(message = "Name can not be null")
    private String cif;
    @Basic
    @Column(name = "name")
    @Size(min = 2,max = 100,message = "Name size must be between 2 and 100")
    @NotNull(message = "Name can not be null")
    private String name;
    @Basic
    @Column(name = "business_name")
    @Size(max = 100,message = "Business name size must be max 100")
    private String businessName;
    @Basic
    @Size(max = 15,message = "Phone size must be max 15")
    @Pattern(regexp = "^\\+?[0-9]*$", message = "The phone number must contain only digits and may start with '+'.")
    @Column(name = "phone")
    private String phone;
    @Basic
    @Size(max = 90,message = "Email size must be max 90")
    @Email(message = "The Email must be valid.")
    @Column(name = "email")
    private String email;
    @Basic
    @Size(min = 2,max = 50,message = "Plain Password size must be between 2 and 50")
    @Column(name = "plain_password")
    @NotNull(message = "Plain Password can not be null")
    private String plainPassword;
    @Basic
    @Size(min = 2,max = 100,message = "Password size must be between 2 and 100")
    @Column(name = "password")
    @NotNull(message = "Password can not be null")
    private String password;
    @Basic
    @Size(max = 255,message = "URL size must be max 255")
    @Column(name = "url")
    @URL(message = "The URL must be valid.")
    private String url;
    @Basic
    @NotNull(message = "Pro can not be null")
    @Column(name = "pro")
    private boolean pro;

    public SellersEntity(int sellerId, String cif, String name, String businessName,
                         String phone, String email, String plainPassword, String password,
                         String url, boolean pro) {
        this.sellerId = sellerId;
        this.cif = cif;
        this.name = name;
        this.businessName = businessName;
        this.phone = phone;
        this.email = email;
        this.plainPassword = plainPassword;
        this.password = password;
        this.url = url;
        this.pro = pro;
    }
    public SellersEntity()
    {}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPro() {
        return pro;
    }

    public void setPro(boolean pro) {
        this.pro = pro;
    }


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
        return sellerId == that.sellerId && Objects.equals(cif, that.cif) && Objects.equals(name, that.name) && Objects.equals(businessName, that.businessName) && Objects.equals(phone, that.phone) && Objects.equals(email, that.email) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellerId, cif, name, businessName, phone, email, plainPassword, password);
    }
}
