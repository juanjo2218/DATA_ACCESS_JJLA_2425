package com.jjla2425.da.unit5.springemplyeeentity.model.daos;

import com.jjla2425.da.unit5.springemplyeeentity.model.entities.DeptEntity;
import org.springframework.data.repository.CrudRepository;

public interface IDeptEntityDAO extends CrudRepository<DeptEntity, Integer> {
}
