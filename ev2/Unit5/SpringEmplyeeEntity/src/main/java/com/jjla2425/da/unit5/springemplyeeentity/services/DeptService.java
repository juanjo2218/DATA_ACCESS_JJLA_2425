package com.jjla2425.da.unit5.springemplyeeentity.services;

import com.jjla2425.da.unit5.springemplyeeentity.model.daos.IDeptEntityDAO;
import com.jjla2425.da.unit5.springemplyeeentity.model.daos.IEmployeeEntityDAO;
import com.jjla2425.da.unit5.springemplyeeentity.model.entities.DeptEntity;
import com.jjla2425.da.unit5.springemplyeeentity.model.entities.EmployeeEntity;
import com.jjla2425.da.unit5.springemplyeeentity.model.DTOS.DeptDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeptService
{
    @Autowired
    private IDeptEntityDAO deptEntityDAO;
    @Autowired
    private IEmployeeEntityDAO employeeEntityDAO;

    public List<DeptEntity> findAllDepts()
    {
        return (List<DeptEntity>) deptEntityDAO.findAll();
    }
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
            Optional<EmployeeEntity> manager = employeeEntityDAO.findByDeptnoAndJob(id, "MANAGER");
            if (manager.isPresent())
                deptDTO.setCodeManager(manager.get().getEmpno());
            else
                deptDTO.setCodeManager(employeeEntityDAO.findByJob("PRESIDENT").getEmpno());
            return ResponseEntity.ok().body(deptDTO);
        }
        else
            return  ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> deleteDept(int id)
    {
        Optional<DeptEntity> dept = deptEntityDAO.findById(id);
        if (dept.isPresent())
        {
            deptEntityDAO.deleteById(id);
            return ResponseEntity.ok().body("Deleted");
        }
        else
            return  ResponseEntity.notFound().build();
    }
    public ResponseEntity<?> updateDept(DeptEntity newDept, int id)
    {
        Optional<DeptEntity> dept = deptEntityDAO.findById(id);
        if (dept.isPresent())
        {
            dept.get().setDname(newDept.getDname());
            dept.get().setLoc(newDept.getLoc());
            deptEntityDAO.save(dept.get());
            return ResponseEntity.ok().body("Update");
        }
        else
            return  ResponseEntity.notFound().build();
    }
    public DeptEntity saveDept(DeptEntity dept)
    {
        return deptEntityDAO.save(dept);
    }
}
