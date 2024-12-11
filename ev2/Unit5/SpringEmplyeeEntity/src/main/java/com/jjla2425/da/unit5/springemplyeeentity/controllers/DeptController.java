package com.jjla2425.da.unit5.springemplyeeentity.controllers;

import com.jjla2425.da.unit5.springemplyeeentity.model.entities.DeptEntity;
import com.jjla2425.da.unit5.springemplyeeentity.services.DTOS.DeptDTO;
import com.jjla2425.da.unit5.springemplyeeentity.services.DeptService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-rest/Department")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping
    public List<DeptEntity> findAllUsers()
    {
        return deptService.findAllDepts();
    }
    @GetMapping("/{id}")
    public ResponseEntity<DeptEntity> findEmployeeById(@PathVariable(value = "id") int id)
    {
        return deptService.findDeptById(id);
    }
    @PostMapping
    public DeptEntity saveEmployee(@RequestBody @Valid DeptEntity dept)
    {
        return deptService.saveDept(dept);
    }
    @GetMapping("/dto/{id}")
    public ResponseEntity<DeptDTO> searchDeptDTOById(@PathVariable(value = "id")int id)
    {
        return deptService.searchDeptDTOById(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id")int id)
    {
        return deptService.deleteDept(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody @Valid DeptEntity newDept,
                                            @PathVariable(value = "id")int id)
    {
        return deptService.updateDept(newDept,id);
    }
}
