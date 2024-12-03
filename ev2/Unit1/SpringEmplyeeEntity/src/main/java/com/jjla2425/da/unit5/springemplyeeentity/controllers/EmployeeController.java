package com.jjla2425.da.unit5.springemplyeeentity.controllers;

import com.jjla2425.da.unit5.springemplyeeentity.model.daos.IEmployeeEntityDAO;
import com.jjla2425.da.unit5.springemplyeeentity.model.entities.EmployeeEntity;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-rest/Employees")
public class EmployeeController
{
    @Autowired
    private IEmployeeEntityDAO employeeEntityDAO;

    @GetMapping
    public List<EmployeeEntity> findAllUsers()
    {
        return (List<EmployeeEntity>) employeeEntityDAO.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEntity> findEmployeeById(@PathVariable(value = "id") int id)
    {
        Optional<EmployeeEntity> employee = employeeEntityDAO.findById(id);
        return employee.isPresent() ? ResponseEntity.ok().body(employee.get()) : ResponseEntity.notFound().build();
    }
    @PostMapping
    public EmployeeEntity saveEmployee(@Validated @RequestBody EmployeeEntity employee)
    {
        return employeeEntityDAO.save(employee);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeEntity newEmployee,
                                            @PathVariable(value = "id")int id)
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
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id")int id)
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