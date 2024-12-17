package com.jjla2425.da.unit5.sellerappspring.services;

import com.jjla2425.da.unit5.sellerappspring.model.daos.IProductsDAO;
import com.jjla2425.da.unit5.sellerappspring.model.entities.CategoriesEntity;
import com.jjla2425.da.unit5.sellerappspring.model.entities.ProductsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductsService {

    @Autowired
    private IProductsDAO productsDAO;

    public List<ProductsEntity> findAllProducts()
    {
        return (List<ProductsEntity>) productsDAO.findAll();
    }
    public ResponseEntity<ProductsEntity> findProductById(int id)
    {
        Optional<ProductsEntity> product = productsDAO.findById(id);
        return product.isPresent() ? ResponseEntity.ok().body(product.get()) : ResponseEntity.notFound().build();
    }
    public List<ProductsEntity> findAllProductsByCategoryAndActive(int idCategory)
    {
        return (List<ProductsEntity>) productsDAO.findAllByCategoryIdAndActive(idCategory,true);
    }
}
