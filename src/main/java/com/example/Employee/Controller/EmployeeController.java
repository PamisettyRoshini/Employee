package com.example.Employee.Controller;

import com.example.Employee.ErrorResponse;
import com.example.Employee.entity.EmployeeEntity;
import com.example.Employee.service.EmployeeService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;


@RestController

@RequestMapping("/employees")
public class EmployeeController {
    private static final Logger logger = LogManager.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService empService;

    @PostMapping("/create")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeEntity createEmployee) {
        try {
            if (createEmployee.getEmpid() == 0 || createEmployee.getFname() == "" || createEmployee.getLname() == "" || createEmployee.getEmail() == "" || createEmployee.getDeptid() == 0 ||
                    createEmployee.getPosition() == "" || createEmployee.getPhnno() == "") {
                String errorMessage = "Error creating employee. Reason: Enter all the field values";
                logger.error(errorMessage);
                return status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessage));
            } else {
                EmployeeEntity createdEmployee = empService.createEmployee(createEmployee);
                logger.info("Employee created successfully. ID: {}", createdEmployee.getEmpid());
                return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            String errorMessage = "Error creating employee" + e.getMessage();
            logger.error(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessage));
        }
    }

    @GetMapping("/hello")
    //@PreAuthorize("hasAuthority('ROLE_USER')")
    public String hello() {
        return "Welcome";
    }

    @GetMapping("/hello1")
    //@PreAuthorize("hasRole('USER')")
    public String hello1() {
        return "Welcome1";
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getEmployeeById(@PathVariable int id) {
        try {
            if (id < 0) {
                String errorMessage = "Invalid empid: " + id;
                logger.warn(errorMessage);
                return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
            }
            EmployeeEntity employee = empService.getEmployeebyId(id);
            if (employee != null) {
                logger.info("Retrieved employee successfully. ID: {}", employee.getEmpid());
                return ResponseEntity.ok(employee);
            } else {
                String errorMessage = "No employee found with id: " + id;
                logger.warn(errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(errorMessage));
            }
        } catch (Exception e) {
            String errorMessage = "An error occurred while fetching employee with id " + id + ": " + e.getMessage();
            logger.error(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(errorMessage));
        }
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteEmployeebyId(@PathVariable int id) {
        try {
            EmployeeEntity employee = empService.getEmployeebyId(id);
            if (employee != null) {
                String result = empService.deleteEmployeebyId(id);
                logger.info("Employee with ID {} deleted successfully.", id);
                return ok(result);
            } else {
                logger.warn("No employee found with id: {}", id);
                return notFound().build();
            }
        } catch (Exception e) {
            logger.error("Unexpected error deleting employee with ID {}: {}", id, e.getMessage());
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error deleting employee.");
        }
    }

    @DeleteMapping("/deleteall")
    public String deleteAllEmployees() {

        return empService.deleteAllEmployees();
    }

    @PutMapping("/{id}")
    //@RolesAllowed("ADMIN")
    //check if the employee id given is valid or not,if so then check all the entered values are not null
    public ResponseEntity<?> updateEmployee(@PathVariable int id, @RequestBody EmployeeEntity updateEmployee) {
        try {
            if (id > 0) {
                if (updateEmployee.getEmpid() == 0 || updateEmployee.getFname() == "" || updateEmployee.getLname() == "" || updateEmployee.getEmail() == "" || updateEmployee.getDeptid() == 0 ||
                        updateEmployee.getPosition() == "" || updateEmployee.getPhnno() == "")
                {
                    String errorMessage = "Error creating employee. Reason: Enter all the field values";
                    logger.error(errorMessage);
                    return status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessage));
                }
                else {
                    EmployeeEntity createdEmployee = empService.createEmployee(updateEmployee);
                    logger.info("Employee created successfully. ID: {}", createdEmployee.getEmpid());
                    return new ResponseEntity<>(updateEmployee, HttpStatus.CREATED);
                }
            } else {
                String errorMessage = "Invalid empid: " + id;
                logger.warn(errorMessage);
                return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
            }
        } catch (Exception e) {
            logger.error("Error updating employee: " + e.getMessage());
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error updating employee.");
        }
    }
}
