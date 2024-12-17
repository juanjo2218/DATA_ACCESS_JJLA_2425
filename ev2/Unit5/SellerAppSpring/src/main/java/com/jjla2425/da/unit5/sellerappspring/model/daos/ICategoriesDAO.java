package com.jjla2425.da.unit5.sellerappspring.model.daos;

import com.jjla2425.da.unit5.sellerappspring.model.entities.CategoriesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriesDAO extends CrudRepository<CategoriesEntity,Integer> {
}
