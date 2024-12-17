package com.jjla2425.da.unit5.sellerappspring.model.daos;

import com.jjla2425.da.unit5.sellerappspring.model.entities.SellersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISellersDAO extends CrudRepository<SellersEntity,Integer> {
}
