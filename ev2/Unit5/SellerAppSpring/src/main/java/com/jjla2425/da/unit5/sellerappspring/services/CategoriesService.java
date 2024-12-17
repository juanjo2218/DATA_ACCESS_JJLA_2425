package com.jjla2425.da.unit5.sellerappspring.services;

import com.jjla2425.da.unit5.sellerappspring.model.daos.ICategoriesDAO;
import com.jjla2425.da.unit5.sellerappspring.model.entities.CategoriesEntity;
import com.jjla2425.da.unit5.sellerappspring.model.entities.ProductsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoriesService
{
    @Autowired
    private ICategoriesDAO categoriesDAO;

    public List<CategoriesEntity> findAllCategories()
    {
        return (List<CategoriesEntity>) categoriesDAO.findAll();
    }
    public ResponseEntity<CategoriesEntity> findCategoryById(int id)
    {
        Optional<CategoriesEntity> categories = categoriesDAO.findById(id);
        return categories.isPresent() ? ResponseEntity.ok().body(categories.get()) : ResponseEntity.notFound().build();
    }
}
