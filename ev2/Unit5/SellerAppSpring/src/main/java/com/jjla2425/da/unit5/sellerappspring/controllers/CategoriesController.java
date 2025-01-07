package com.jjla2425.da.unit5.sellerappspring.controllers;

import com.jjla2425.da.unit5.sellerappspring.model.entities.CategoriesEntity;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellersEntity;
import com.jjla2425.da.unit5.sellerappspring.services.CategoriesService;
import com.jjla2425.da.unit5.sellerappspring.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-rest/Categories")
public class CategoriesController {
    @Autowired
    private CategoriesService categoriesService;
    @GetMapping
    public List<CategoriesEntity> findAllCategories()
    {
        return categoriesService.findAllCategories();
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoriesEntity> findCategoryById(@PathVariable(value = "id") int id)
    {
        return categoriesService.findCategoryById(id);
    }
}
