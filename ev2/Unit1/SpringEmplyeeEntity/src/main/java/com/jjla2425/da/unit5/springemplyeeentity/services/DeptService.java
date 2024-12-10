package com.jjla2425.da.unit5.springemplyeeentity.services;

import com.jjla2425.da.unit5.springemplyeeentity.model.daos.IDeptEntityDAO;
import com.jjla2425.da.unit5.springemplyeeentity.model.daos.IEmployeeEntityDAO;
import com.jjla2425.da.unit5.springemplyeeentity.model.entities.DeptEntity;
import com.jjla2425.da.unit5.springemplyeeentity.services.DTOS.DeptDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeptService
{
    @Autowired
    private IDeptEntityDAO deptEntityDAO;
    @Autowired
    private IEmployeeEntityDAO employeeEntityDAO;
    public ResponseEntity<DeptEntity> findDeptById(int id)
    {
        Optional<DeptEntity> dept = deptEntityDAO.findById(id);
        return dept.isPresent() ? ResponseEntity.ok().body(dept.get()) : ResponseEntity.notFound().build();
    }
    public ResponseEntity<DeptDTO> searchDeptDTOById(int id)
    {
        Optional<DeptEntity> dept = deptEntityDAO.findById(id);
        if (dept.isPresent())
        {
            DeptDTO deptDTO = new DeptDTO();
            deptDTO.setDeptno(dept.get().getDeptno());
            deptDTO.setLoc(dept.get().getLoc());
            deptDTO.setDname(dept.get().getDname());
            deptDTO.setEmployeesCount(employeeEntityDAO.findAllByDeptno(id).size());
            deptDTO.setCodeManager(employeeEntityDAO.findByDeptnoAndJob(id,"MANAGER").getEmpno());
            return ResponseEntity.ok().body(deptDTO);
        }
        else
            return  ResponseEntity.notFound().build();
    }
}
