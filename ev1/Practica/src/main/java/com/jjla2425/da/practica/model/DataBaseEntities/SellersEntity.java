package com.jjla2425.da.practica.model.DataBaseEntities;

import jakarta.persistence.*;
import org.json.JSONException;
import org.json.JSONObject;

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
    @Basic
    @Column(name = "url")
    private String url;
    @Basic
    @Column(name = "pro")
    private boolean pro;

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
        return sellerId == that.sellerId && Objects.equals(cif, that.cif) && Objects.equals(name, that.name) && Objects.equals(businessName, that.businessName) && Objects.equals(phone, that.phone) && Objects.equals(email, that.email) && Objects.equals(plainPassword, that.plainPassword) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellerId, cif, name, businessName, phone, email, plainPassword, password);
    }

    public void ToSellerEntity(JSONObject jsonObject)
    {
        setCif(jsonObject.optString("cif", ""));
        setName(jsonObject.optString("name", ""));
        setBusinessName(jsonObject.optString("businessName", ""));
        setEmail(jsonObject.optString("email", ""));
        setSellerId(Integer.parseInt(jsonObject.optString("sellerId", "")));
        setPhone(jsonObject.optString("phone", ""));
        setUrl(jsonObject.optString("url", ""));
        setPassword(jsonObject.optString("password", ""));
        setPlainPassword(jsonObject.optString("plainPassword", ""));
        setPro(Boolean.parseBoolean(jsonObject.optString("pro", "")));
    }

    public String toJSON() {
        try {
            return new JSONObject()
                    .put("name", name)
                    .put("businessName", businessName)
                    .put("email",email)
                    .put("phone",phone)
                    .put("url",url)
                    .put("password",password)
                    .put("plainPassword",plainPassword)
                    .put("pro",pro)
                    .put("cif",cif)
                    .put("sellerId",sellerId)
                    .toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
