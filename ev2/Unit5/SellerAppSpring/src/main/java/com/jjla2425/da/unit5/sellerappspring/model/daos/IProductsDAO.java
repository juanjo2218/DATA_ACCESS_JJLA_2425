package com.jjla2425.da.unit5.sellerappspring.model.daos;

import com.jjla2425.da.unit5.sellerappspring.model.entities.ProductsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface IProductsDAO extends CrudRepository<ProductsEntity,Integer> {
   List<ProductsEntity> getProductsByCategoryAndSellerIdRemaning(int p_idseller,int p_idcategory);

   List<ProductsEntity> findAllByCategoryIdAndActive(int idCategory, boolean active);
}
