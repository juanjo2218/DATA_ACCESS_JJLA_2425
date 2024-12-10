package com.jjla2425.da.unit5.springemplyeeentity.model.daos;

import com.jjla2425.da.unit5.springemplyeeentity.model.entities.DeptEntity;
import com.jjla2425.da.unit5.springemplyeeentity.model.entities.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface IEmployeeEntityDAO extends CrudRepository<EmployeeEntity, Integer> {
    ArrayList<EmployeeEntity> findAllByDeptno(int deptno);

    EmployeeEntity findByDeptnoAndJob(int deptno, String job);
}
