package com.jjla2425.da.unit5.sellerappspring.model.daos;

import com.jjla2425.da.unit5.sellerappspring.model.DTOS.SellerDTO;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISellersDAO extends CrudRepository<SellersEntity,Integer> {
    Optional<SellersEntity> findByCif(String cif);
}
