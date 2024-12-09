package com.jjla2425.da.unit5.springemplyeeentity.model.DTOS;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;

public class EmployeeDTO
{
    private int empno;

    private String ename;

    private String job;
    private String namedept;
    private String ubidept;

    public int getEmpno() {
        return empno;
    }

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getNamedept() {
        return namedept;
    }

    public void setNamedept(String namedept) {
        this.namedept = namedept;
    }

    public String getUbidept() {
        return ubidept;
    }

    public void setUbidept(String ubidept) {
        this.ubidept = ubidept;
    }

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }

    private Integer deptno;

}
