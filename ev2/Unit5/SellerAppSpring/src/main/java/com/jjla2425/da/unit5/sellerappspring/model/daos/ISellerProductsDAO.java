package com.jjla2425.da.unit5.sellerappspring.model.daos;

import com.jjla2425.da.unit5.sellerappspring.model.entities.SellerProductsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISellerProductsDAO extends CrudRepository<SellerProductsEntity,Integer> {
    List<SellerProductsEntity> getSellerProductsBySellerIdActives(int sellerId);

    Optional<SellerProductsEntity> findBySellerIdAndProductId(int sellerId,int productId);
}
