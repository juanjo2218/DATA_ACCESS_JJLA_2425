package com.jjla2425.da.unit5.sellerappspring.model.DTOS;

import com.jjla2425.da.unit5.sellerappspring.model.entities.SellersEntity;
import jakarta.validation.constraints.AssertTrue;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Size;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Objects;

public class SellerDTO extends SellersEntity {

    @Size(max = 50,message = "The password cannot be longer than 50 characters")
    private String newPassword;
    @Size(max = 50,message = "The password cannot be longer than 50 characters")
    private String repeatNewPassword;



    @AssertTrue(message = "Passwords must match")
    public boolean isPasswordsMatch() {
        return newPassword != null && newPassword.equals(repeatNewPassword);
    }
    public static SellerDTO toDTO(SellersEntity seller) {
        if (seller == null) {
            return null;
        }

        SellerDTO dto = new SellerDTO();
        dto.setSellerId(seller.getSellerId());
        dto.setCif(seller.getCif());
        dto.setName(seller.getName());
        dto.setBusinessName(seller.getBusinessName());
        dto.setPhone(seller.getPhone());
        dto.setEmail(seller.getEmail());
        dto.setPlainPassword(seller.getPlainPassword());
        dto.setPassword(seller.getPassword());
        dto.setUrl(seller.getUrl());
        dto.setPro(seller.isPro());

        return dto;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SellerDTO seller = (SellerDTO) obj;
        return Objects.equals(getName(), seller.getName()) &&
                Objects.equals(getBusinessName(), seller.getBusinessName()) &&
                Objects.equals(getPhone(), seller.getPhone()) &&
                Objects.equals(getEmail(), seller.getEmail()) &&
                Objects.equals(getUrl(), seller.getUrl()) &&
                (getNewPassword().isEmpty() || Objects.equals(DigestUtils.md5Hex(getNewPassword()).toUpperCase(), seller.getPassword()));
    }
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatNewPassword() {
        return repeatNewPassword;
    }

    public void setRepeatNewPassword(String repeatNewPassword) {
        this.repeatNewPassword = repeatNewPassword;
    }


}
