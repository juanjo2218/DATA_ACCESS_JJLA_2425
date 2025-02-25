package com.jjla2425.da.unit5.springemplyeeentity.controllers;

import com.jjla2425.da.unit5.springemplyeeentity.model.DTOS.EmployeeDTO;
import com.jjla2425.da.unit5.springemplyeeentity.model.entities.EmployeeEntity;
import com.jjla2425.da.unit5.springemplyeeentity.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-rest/Employees")
public class EmployeeController
{
    @Autowired
    private EmployeeService employeeService;
    @GetMapping
    public List<EmployeeEntity> findAllUsers()
    {
        return employeeService.findAllUsers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEntity> findEmployeeById(@PathVariable(value = "id") int id)
    {
        return employeeService.findEmployeeById(id);
    }
    @PostMapping
    public EmployeeEntity saveEmployee(@Valid @RequestBody EmployeeEntity employee)
    {
        return employeeService.saveEmployee(employee);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody @Valid EmployeeEntity newEmployee,
                                            @PathVariable(value = "id")int id)
    {
        return employeeService.updateEmployee(newEmployee,id);
    }
    @GetMapping("/dto/{id}")
    public ResponseEntity<EmployeeDTO> searchEmployeeDTOById(@PathVariable(value = "id")int id)
    {
        return employeeService.searchEmployeeDTOById(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id")int id)
    {
       return employeeService.deleteEmployee(id);
    }
}
