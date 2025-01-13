package com.jjla2425.da.unit5.sellerappspring.controllers;

import com.jjla2425.da.unit5.sellerappspring.model.entities.ProductsEntity;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellersEntity;
import com.jjla2425.da.unit5.sellerappspring.services.ProductsService;
import com.jjla2425.da.unit5.sellerappspring.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api-rest/Products")
public class ProductController
{
    @Autowired
    private ProductsService productsService;
    @Autowired
    private SellerService sellerProductService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductsEntity> findProductById(@PathVariable(value = "id") int id)
    {
        return productsService.findProductById(id);
    }
    @PutMapping("/{idCategory}")
    public List<ProductsEntity> findAllProductsByCategoryAndActive(@PathVariable(value = "idCategory")int idCategory)
    {
        return productsService.findAllProductsByCategoryAndActive(idCategory);
    }

    @GetMapping("/{idCategory}/{idSeller}")
    public List<ProductsEntity> getProductsSellerRemaining(@PathVariable(value = "idCategory") Integer idCategory,
                                                           @PathVariable (value = "idSeller") Integer idSeller) {
        return productsService.getProductsSellerRemaining(idCategory,idSeller);
    }
}
