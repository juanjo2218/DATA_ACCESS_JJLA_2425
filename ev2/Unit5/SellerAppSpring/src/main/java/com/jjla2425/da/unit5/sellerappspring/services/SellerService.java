package com.jjla2425.da.unit5.sellerappspring.services;

import com.jjla2425.da.unit5.sellerappspring.model.DTOS.SellerDTO;
import com.jjla2425.da.unit5.sellerappspring.model.DTOS.SellerExamenDTO;
import com.jjla2425.da.unit5.sellerappspring.model.daos.ISellerProductsDAO;
import com.jjla2425.da.unit5.sellerappspring.model.daos.ISellersDAO;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellerProductsEntity;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellersEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {
    @Autowired
    private ISellersDAO sellersDAO;
    @Autowired
    private ISellerProductsDAO sellerProductsDAO;
    public ResponseEntity<SellersEntity> findSellerBycif(String CIF)
    {
        Optional<SellersEntity> seller = sellersDAO.findByCif(CIF);
        return seller.isPresent() ? ResponseEntity.ok().body(seller.get()) : ResponseEntity.notFound().build();
    }
    public ResponseEntity<?> updateSeller(SellerDTO sellersEntity, String CIF)
    {
        Optional<SellersEntity> seller = sellersDAO.findByCif(CIF);
        if (seller.isPresent())
        {
            seller.get().setBusinessName(sellersEntity.getBusinessName());
            seller.get().setEmail(sellersEntity.getEmail());
            seller.get().setName(sellersEntity.getName());
            seller.get().setPhone(sellersEntity.getPhone());
            if (!sellersEntity.getNewPassword().isEmpty())
            {
                seller.get().setPassword(DigestUtils.md5Hex(sellersEntity.getNewPassword()).toUpperCase());
                seller.get().setPlainPassword(sellersEntity.getNewPassword());
            }
            seller.get().setPro(sellersEntity.isPro());
            seller.get().setUrl(sellersEntity.getUrl());
            sellersDAO.save(seller.get());
            return ResponseEntity.ok().body("Update");
        }
        else
            return  ResponseEntity.notFound().build();
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
            if (!sellersEntity.getPassword().isEmpty())
            {
                seller.get().setPassword(DigestUtils.md5Hex(sellersEntity.getPassword()).toUpperCase());
                seller.get().setPlainPassword(sellersEntity.getPassword());
            }
            seller.get().setPro(sellersEntity.isPro());
            seller.get().setUrl(sellersEntity.getUrl());
            sellersDAO.save(seller.get());
            return ResponseEntity.ok().body("Update");
        }
        else
            return  ResponseEntity.notFound().build();
    }

    public List<SellerExamenDTO> findSellersByProductId(int idProduct) {
        List<SellerProductsEntity> sellerProductsEntities =  sellerProductsDAO.findByProductId(idProduct);
        List <SellerExamenDTO> sellers = null;
        for (SellerProductsEntity sellerProductsEntity : sellerProductsEntities) {
            assert false;
            Optional<SellersEntity> sellers1 = sellersDAO.findById(sellerProductsEntity.getSellerId());
            sellers.add(new SellerExamenDTO(sellers1.get().getSellerId(),
                    sellers1.get().getCif(),sellers1.get().getPhone(),sellers1.get().getEmail()));
        }
        return sellers;
    }
}
