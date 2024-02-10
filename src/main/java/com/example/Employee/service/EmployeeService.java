package com.example.Employee.service;

import com.example.Employee.entity.EmployeeEntity;
import com.example.Employee.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository empRepository;

    public EmployeeEntity getEmployeebyId(int id) {
        EmployeeEntity emp = empRepository.findById(id).orElse(null);
        if(emp!=null) {
            return new EmployeeEntity(emp.getEmpid(),emp.getFname(),emp.getLname(),emp.getEmail(),emp.getDeptid(),emp.getPosition(),emp.getPhnno());
        }
        return null;
    }


    public EmployeeEntity createEmployee(EmployeeEntity createEmployee) {
        EmployeeEntity employee = new EmployeeEntity();

        employee.setEmpid(createEmployee.getEmpid());
        employee.setFname(createEmployee.getFname());
        employee.setLname(createEmployee.getLname());
        employee.setEmail(createEmployee.getEmail());
        employee.setDeptid(createEmployee.getDeptid());
        employee.setPosition(createEmployee.getPosition());
        employee.setPhnno(createEmployee.getPhnno());
        return empRepository.save(employee);

    }

    public EmployeeEntity updateEmployee(int id, EmployeeEntity updateEmployee) {
        EmployeeEntity existEmp = empRepository.findById(id).orElse(null);
        if (existEmp != null) {
            existEmp.setEmpid(updateEmployee.getEmpid());
            existEmp.setFname(updateEmployee.getFname());
            existEmp.setLname(updateEmployee.getLname());
            existEmp.setEmail(updateEmployee.getEmail());
            existEmp.setDeptid(updateEmployee.getDeptid());
            existEmp.setPosition(updateEmployee.getPosition());
            existEmp.setPhnno(updateEmployee.getPhnno());
            EmployeeEntity empEntity = empRepository.save(existEmp);
            return new EmployeeEntity(empEntity.getEmpid(), empEntity.getFname(), empEntity.getLname(), empEntity.getEmail(),
                    empEntity.getDeptid(), empEntity.getPosition(), empEntity.getPhnno());
        }

        return null;
    }

    public String deleteEmployeebyId(int id) {
        empRepository.deleteById(id);
        return "deleted";

    }


    public String deleteAllEmployees() {
        empRepository.deleteAll();
        return "All employees deleted";
    }
}

