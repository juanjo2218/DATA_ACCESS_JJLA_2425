package com.jjla2425.da.unit5.springemplyeeentity.model.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "employee", schema = "public", catalog = "Employees")
public class EmployeeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "empno")
    private int empno;
    @Basic
    @Column(name = "ename")
    private String ename;
    @Basic
    @Column(name = "job")
    private String job;
    @Basic
    @Column(name = "deptno")
    private Integer deptno;

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

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity that = (EmployeeEntity) o;
        return empno == that.empno && Objects.equals(ename, that.ename) && Objects.equals(job, that.job) && Objects.equals(deptno, that.deptno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empno, ename, job, deptno);
    }
}
