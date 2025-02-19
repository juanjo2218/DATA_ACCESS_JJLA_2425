package com.jjla2425.da.unit5.sellerappspring.model.DTOS;

public class SellerExamenDTO {
    String CIF;
    int sellerId;
    String email;
    String phone;

    public SellerExamenDTO(int sellerId, String cif, String phone, String email) {
        this.CIF = cif;
        this.phone = phone;
        this.email = email;
        this.sellerId = sellerId;
    }
}
