package com.example.Employee.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="EMP1")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer empid;
    @NotBlank(message = "fname is required")
    String fname;
    String lname;
    String email;
    Integer deptid;
    String position;
    String phnno;
    public Integer getEmpid() {
        return empid;
    }
    public void setEmpid(Integer empid) {
        this.empid = empid;
    }
    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public String getLname() {
        return lname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getDeptid() {
        return deptid;
    }
    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getPhnno() {
        return phnno;
    }
    public void setPhnno(String phnno) {
        this.phnno = phnno;
    }
    public EmployeeEntity(Integer empid, String fname, String lname, String email, Integer deptid, String position,
                          String phnno) {
        super();
        this.empid = empid;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.deptid = deptid;
        this.position = position;
        this.phnno = phnno;
    }
    @Override
    public String toString() {
        return "EmployeeEntity [empid=" + empid + ", fname=" + fname + ", lname=" + lname + ", email=" + email
                + ", deptid=" + deptid + ", position=" + position + ", phnno=" + phnno + "]";
    }
    public EmployeeEntity() {
        super();
    }


}