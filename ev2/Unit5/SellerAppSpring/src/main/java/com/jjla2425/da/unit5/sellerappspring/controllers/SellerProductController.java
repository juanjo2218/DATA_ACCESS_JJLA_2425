package com.jjla2425.da.unit5.sellerappspring.controllers;

import com.jjla2425.da.unit5.sellerappspring.model.entities.CategoriesEntity;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellerProductsEntity;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellersEntity;
import com.jjla2425.da.unit5.sellerappspring.services.CategoriesService;
import com.jjla2425.da.unit5.sellerappspring.services.SellerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-rest/SellerProducts")
public class SellerProductController {
    @Autowired
    private SellerProductService sellerProductService;

    @GetMapping
    public List<SellerProductsEntity> findAllSellerProducts() {
        return sellerProductService.findAllSellersProducts();
    }
    @PostMapping
    public SellerProductsEntity saveSellerProductsEntity(@RequestBody SellerProductsEntity sellerProductsEntity)
    {
        return sellerProductService.saveSellerProduct(sellerProductsEntity);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSeller(@RequestBody SellerProductsEntity sellerProductsEntity,
                                          @PathVariable(value = "id")int id)
    {
        return sellerProductService.updateSellerProduct(sellerProductsEntity,id);
    }
}