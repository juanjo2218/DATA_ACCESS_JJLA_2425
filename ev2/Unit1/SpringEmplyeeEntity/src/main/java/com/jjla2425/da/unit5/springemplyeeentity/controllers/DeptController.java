package com.jjla2425.da.unit5.springemplyeeentity.controllers;

import com.jjla2425.da.unit5.springemplyeeentity.services.DTOS.DeptDTO;
import com.jjla2425.da.unit5.springemplyeeentity.services.DTOS.EmployeeDTO;
import com.jjla2425.da.unit5.springemplyeeentity.services.DeptService;
import com.jjla2425.da.unit5.springemplyeeentity.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-rest/Department")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping("/dto/{id}")
    public ResponseEntity<DeptDTO> searchDeptDTOById(@PathVariable(value = "id")int id)
    {
        return deptService.searchDeptDTOById(id);
    }
}
