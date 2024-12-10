package com.jjla2425.da.unit5.springemplyeeentity.services.DTOS;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

public class DeptDTO
{
    private int deptno;
    private String dname;
    private String loc;
    private int employeesCount;
    private int codeManager;

    public int getDeptno() {
        return deptno;
    }

    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public int getEmployeesCount() {
        return employeesCount;
    }

    public void setEmployeesCount(int employeesCount) {
        this.employeesCount = employeesCount;
    }

    public int getCodeManager() {
        return codeManager;
    }

    public void setCodeManager(int codeManager) {
        this.codeManager = codeManager;
    }
}
