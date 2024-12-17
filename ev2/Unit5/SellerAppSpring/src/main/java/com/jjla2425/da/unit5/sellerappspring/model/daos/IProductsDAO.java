package com.jjla2425.da.unit5.sellerappspring.model.daos;

import com.jjla2425.da.unit5.sellerappspring.model.entities.ProductsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface IProductsDAO extends CrudRepository<ProductsEntity,Integer> {
   List<ProductsEntity> findAllByCategoryIdAndActive(int categoryId, boolean active);
}
