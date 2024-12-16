package com.jjla2425.da.unit5.springemplyeeentity.services;

import com.jjla2425.da.unit5.springemplyeeentity.model.daos.IDeptEntityDAO;
import com.jjla2425.da.unit5.springemplyeeentity.model.daos.IEmployeeEntityDAO;
import com.jjla2425.da.unit5.springemplyeeentity.model.entities.DeptEntity;
import com.jjla2425.da.unit5.springemplyeeentity.model.entities.EmployeeEntity;
import com.jjla2425.da.unit5.springemplyeeentity.model.DTOS.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EmployeeService
{
    @Autowired
    private IEmployeeEntityDAO employeeEntityDAO;
    @Autowired
    private IDeptEntityDAO deptEntityDAO;
    public ResponseEntity<EmployeeEntity> findEmployeeById(int id)
    {
        Optional<EmployeeEntity> employee = employeeEntityDAO.findById(id);
        return employee.isPresent() ? ResponseEntity.ok().body(employee.get()) : ResponseEntity.notFound().build();
    }
    public List<EmployeeEntity> findAllUsers()
    {
        return (List<EmployeeEntity>) employeeEntityDAO.findAll();
    }
    public EmployeeEntity saveEmployee(EmployeeEntity employee)
    {
        return employeeEntityDAO.save(employee);
    }
    public ResponseEntity<?> updateEmployee(EmployeeEntity newEmployee, int id)
    {
        Optional<EmployeeEntity> employee = employeeEntityDAO.findById(id);
        if (employee.isPresent())
        {
            employee.get().setEname(newEmployee.getEname());
            employee.get().setJob(newEmployee.getJob());
            employee.get().setDeptno(newEmployee.getDeptno());
            employeeEntityDAO.save(employee.get());
            return ResponseEntity.ok().body("Update");

        }
        else
            return  ResponseEntity.notFound().build();
    }
    public ResponseEntity<EmployeeDTO> searchEmployeeDTOById(int id)
    {
        Optional<EmployeeEntity> employee = employeeEntityDAO.findById(id);
        if (employee.isPresent())
        {
            Optional<DeptEntity> departament = deptEntityDAO.findById(employee.get().getDeptno());
            EmployeeDTO empleadosDTO = new EmployeeDTO();
            empleadosDTO.setEmpno(employee.get().getEmpno());
            empleadosDTO.setEname(employee.get().getEname());
            empleadosDTO.setJob(employee.get().getJob());
            empleadosDTO.setDeptno(employee.get().getDeptno());
            empleadosDTO.setNamedept(departament.get().getDname());
            empleadosDTO.setUbidept(departament.get().getLoc());
            return ResponseEntity.ok().body(empleadosDTO);
        }
        else
            return  ResponseEntity.notFound().build();
    }
    public ResponseEntity<?> deleteEmployee(int id)
    {
        Optional<EmployeeEntity> employee = employeeEntityDAO.findById(id);
        if (employee.isPresent())
        {
            employeeEntityDAO.deleteById(id);
            return ResponseEntity.ok().body("Deleted");
        }
        else
            return  ResponseEntity.notFound().build();
    }
}
