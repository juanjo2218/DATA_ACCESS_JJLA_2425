package com.jjla2425.da.unit5.sellerappspring.controllers;

import com.jjla2425.da.unit5.sellerappspring.model.entities.SellerProductsEntity;
import com.jjla2425.da.unit5.sellerappspring.services.SellerProductService;
import jakarta.validation.Valid;
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
    public SellerProductsEntity saveSellerProductsEntity(@Valid @RequestBody SellerProductsEntity sellerProductsEntity)
    {
        return sellerProductService.saveSellerProduct(sellerProductsEntity);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSellerProduct(@RequestBody SellerProductsEntity sellerProductsEntity,@Valid
                                                 @PathVariable(value = "id")int id)
    {
        return sellerProductService.updateSellerProduct(sellerProductsEntity);
    }
    @GetMapping("/{idSeller}")
    public List<SellerProductsEntity> findAllSellerProductsByIdSeller( @PathVariable(value = "idSeller")int idSeller)
    {
        return sellerProductService.findAllSellerProductsByIdSellerAndActive(idSeller);
    }
}