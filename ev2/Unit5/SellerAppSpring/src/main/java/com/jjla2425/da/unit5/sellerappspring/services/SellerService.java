package com.jjla2425.da.unit5.sellerappspring.services;

import com.jjla2425.da.unit5.sellerappspring.model.daos.ISellersDAO;
import com.jjla2425.da.unit5.sellerappspring.model.entities.ProductsEntity;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {
    @Autowired
    private ISellersDAO sellersDAO;

    public List<SellersEntity> findAllSellers()
    {
        return (List<SellersEntity>) sellersDAO.findAll();
    }
    public ResponseEntity<SellersEntity> findSellerBycif(String CIF)
    {
        Optional<SellersEntity> seller = sellersDAO.findByCif(CIF);
        return seller.isPresent() ? ResponseEntity.ok().body(seller.get()) : ResponseEntity.notFound().build();
    }
    public ResponseEntity<?> updateSeller(SellersEntity sellersEntity, String CIF)
    {
        Optional<SellersEntity> seller = sellersDAO.findByCif(CIF);
        if (seller.isPresent())
        {
            seller.get().setBusinessName(sellersEntity.getBusinessName());
            seller.get().setEmail(sellersEntity.getEmail());
            seller.get().setName(sellersEntity.getName());
            seller.get().setPhone(sellersEntity.getPhone());
            seller.get().setPassword(sellersEntity.getPassword());
            seller.get().setPlainPassword(sellersEntity.getPlainPassword());
            seller.get().setPro(sellersEntity.isPro());
            seller.get().setUrl(sellersEntity.getUrl());
            sellersDAO.save(seller.get());
            return ResponseEntity.ok().body("Update");
        }
        else
            return  ResponseEntity.notFound().build();
    }
}
