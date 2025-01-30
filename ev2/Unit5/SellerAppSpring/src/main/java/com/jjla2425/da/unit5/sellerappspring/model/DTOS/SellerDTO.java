package com.jjla2425.da.unit5.sellerappspring.model.DTOS;

import com.jjla2425.da.unit5.sellerappspring.model.entities.SellersEntity;
import jakarta.validation.constraints.AssertTrue;
import jakarta.persistence.Transient;

public class SellerDTO extends SellersEntity {

    private String newPassword;
    private String repeatNewPassword;

    @Transient  // Evita que se almacene en la base de datos
    private boolean passwordsMatch;

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
