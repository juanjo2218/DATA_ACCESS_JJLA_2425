package com.jjla2425.da.unit5.springemplyeeentity.model.daos;

import com.jjla2425.da.unit5.springemplyeeentity.model.entities.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

public interface IEmployeeEntityDAO extends CrudRepository<EmployeeEntity, Integer> {
}
