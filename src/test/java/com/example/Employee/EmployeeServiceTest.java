package com.example.Employee;

import com.example.Employee.entity.EmployeeEntity;
import com.example.Employee.repo.EmployeeRepository;
import com.example.Employee.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService empService;
    @Mock
    private EmployeeRepository empRepository;


    @Test
    void testGetEmployeeById() {
        int empid = 1;
        when(empRepository.findById(empid)).thenReturn(Optional.of(new EmployeeEntity(1, "John", "Doe", "john@example.com", 9, "Developer", "1234567890")));
        EmployeeEntity retrievedEmployee = empService.getEmployeebyId(empid);
        Assertions.assertNotNull(retrievedEmployee);
        assertEquals("John", retrievedEmployee.getFname());
        assertEquals("john@example.com", retrievedEmployee.getEmail());
        verify(empRepository, times(1)).findById(empid);
    }

    @Test
    void testdeleteEmployeebyId() {
        int empid = 1;
        empService.deleteEmployeebyId(empid);
        verify(empRepository).deleteById(empid);
    }

    @Test
    public void testDeleteAllEmployees() {
        empService.deleteAllEmployees();
        verify(empRepository).deleteAll();
    }

    @Test
    void testUpdateEmployee() {
        int empid = 2;

        EmployeeEntity updateEmployee = new EmployeeEntity(2, "UpdatedName", "Doe", "email@example.com", 10, "UpdatedPosition", "1234567890");
        EmployeeEntity existingEntity = new EmployeeEntity(2, "John", "Doe", "john@example.com", 9, "Developer", "9876543210");
        when(empRepository.findById(empid)).thenReturn(Optional.of(existingEntity));
        when(empRepository.save(existingEntity)).thenReturn(updateEmployee);
        EmployeeEntity result = empService.updateEmployee(2, updateEmployee);
        Assertions.assertNotNull(result);
        assertEquals(updateEmployee.getFname(), result.getFname());
        assertEquals(updateEmployee.getPosition(), result.getPosition());
        verify(empRepository, times(1)).findById(empid);
        verify(empRepository, times(1)).save(existingEntity);
    }

}

