package com.jjla2425.da.unit5.springemplyeeentity.controllers;

import com.jjla2425.da.unit5.springemplyeeentity.model.entities.DeptEntity;
import com.jjla2425.da.unit5.springemplyeeentity.model.entities.EmployeeEntity;
import com.jjla2425.da.unit5.springemplyeeentity.services.DeptService;
import com.jjla2425.da.unit5.springemplyeeentity.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PageWeb
{
    @Autowired
    private DeptService deptService;
    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/")
    public String showIndex(Model model)
    {
        return "index";
    }
    @GetMapping("/viewdepartments")
    public String interDepartmental(Model model)
    {
        List<DeptEntity> deptEntityList = deptService.findAllDepts();
        model.addAttribute("departments",deptEntityList);
        return "viewdepartments";
    }
    @GetMapping("/viewemployees")
    public String interEmployees(Model model)
    {
        List<EmployeeEntity> employeeEntities = employeeService.findAllUsers();
        model.addAttribute("employees",employeeEntities);
        return "viewemployees";
    }
    @GetMapping("/adddepartmentd")
    public String createDepartment(Model model) {
        model.addAttribute("department", new DeptEntity());
        return "adddepartmentd";
    }

    @PostMapping("/adddepartmentd")
    public String addDepartment(@ModelAttribute @Valid DeptEntity deptEntity) {
        if (!deptService.existsById(deptEntity.getDeptno())) {
            deptService.saveDepartment(deptEntity);
        }
        return "adddepartmentd";
    }
    @GetMapping("/addemployee")
    public String createEmployee(Model model) {
        model.addAttribute("employee", new EmployeeEntity());
        return "addemployee";
    }

    @PostMapping("/addemployee")
    public String addEmployee(@ModelAttribute @Valid EmployeeEntity employeeEntity) {
        if (!employeeService.existsById(employeeEntity.getDeptno())) {
            employeeService.saveEmployee(employeeEntity);
        }
        return "addemployee";
    }
}
