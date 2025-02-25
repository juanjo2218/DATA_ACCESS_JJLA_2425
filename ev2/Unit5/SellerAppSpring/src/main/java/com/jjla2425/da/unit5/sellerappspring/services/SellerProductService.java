package com.jjla2425.da.unit5.sellerappspring.services;

import com.jjla2425.da.unit5.sellerappspring.model.daos.ISellerProductsDAO;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellerProductsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SellerProductService {
    @Autowired
    private ISellerProductsDAO sellerProductsDAO;
    public List<SellerProductsEntity> findAllSellersProducts()
    {
        return (List<SellerProductsEntity>) sellerProductsDAO.findAll();
    }
    public ResponseEntity<SellerProductsEntity> findSellerProductById(int id)
    {
        Optional<SellerProductsEntity> sellerProduct = sellerProductsDAO.findById(id);
        return sellerProduct.isPresent() ? ResponseEntity.ok().body(sellerProduct.get()) : ResponseEntity.notFound().build();
    }
    public ResponseEntity<?> updateSellerProduct(SellerProductsEntity sellerProductsEntity)
    {
        Optional<SellerProductsEntity> sellerProduct = sellerProductsDAO.findBySellerIdAndProductId(sellerProductsEntity.getSellerId(),sellerProductsEntity.getProductId());
        if (sellerProduct.isPresent())
        {
            sellerProduct.get().setOfferEndDate(sellerProductsEntity.getOfferEndDate());
            sellerProduct.get().setOfferStartDate(sellerProductsEntity.getOfferStartDate());
            sellerProduct.get().setOfferPrice(sellerProductsEntity.getOfferPrice());
            sellerProductsDAO.save(sellerProduct.get());
            return ResponseEntity.ok().body("Update");
        }
        else
            return  ResponseEntity.notFound().build();
    }
    public Optional<SellerProductsEntity> findBySellerIdAndProductId(int sellerId, int productId)
    {
        return sellerProductsDAO.findBySellerIdAndProductId(sellerId,productId);
    }
    public SellerProductsEntity  saveSellerProduct(SellerProductsEntity sellerProductsEntity)
    {
        return sellerProductsDAO.save(sellerProductsEntity);
    }

    public List<SellerProductsEntity> findAllSellerProductsByIdSellerAndActive(int idSeller) {
        return sellerProductsDAO.getSellerProductsBySellerIdActives(idSeller);
    }

    public List<SellerProductsEntity> findAllSellerProductsByIdSellerAndActiveExamen(int idSeller,boolean check) {
        List<SellerProductsEntity> sellerProductsEntities = sellerProductsDAO.getSellerProductsBySellerIdActives(idSeller);
        if (check)
        {
            LocalDate today = LocalDate.now();
            for (int i = 0; i < sellerProductsEntities.size(); i++) {
                if (sellerProductsEntities.get(i).getOfferStartDate() != null)
                {
                    if (sellerProductsEntities.get(i).getOfferStartDate().isBefore(today))
                    {
                        sellerProductsEntities.remove(sellerProductsEntities.get(i));
                        i--;
                    }
                }
            }
        }
        return sellerProductsEntities;
    }
    public List<SellerProductsEntity> findByProductId(int productId) {
        return sellerProductsDAO.findByProductId(productId);
    }
}
