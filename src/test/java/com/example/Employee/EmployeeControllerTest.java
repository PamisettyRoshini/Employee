package com.example.Employee;

import com.example.Employee.Controller.EmployeeController;
import com.example.Employee.entity.EmployeeEntity;
import com.example.Employee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService empService;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void test1GetEmployeeById() throws Exception {
        EmployeeEntity employee = new EmployeeEntity
                (6, "John", "Doe", "john@example.com", 9, "Developer", "1234567890");
        when(empService.getEmployeebyId(6)).thenReturn(employee);
        mockMvc.perform(get("/employees/6")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.empid").value(6))
                .andExpect(jsonPath("$.fname").value("John"));
        verify(empService).getEmployeebyId(6);
    }


    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testDeleteAllEmployeesUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/employees/deleteall")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testDeleteEmployeeById() throws Exception {
        when(empService.deleteEmployeebyId(2)).thenReturn("deleted");
        mockMvc.perform(delete("/employees/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
               .andExpect(content().string("deleted"));

        verify(empService).deleteEmployeebyId(2);
    }
    @Test
//    @WithMockUser(username = "user", roles = {"USER", "ADMIN"})
    public void testDeleteAllEmployees() throws Exception {
        when(empService.deleteAllEmployees()).thenReturn("All employees deleted successfully");
        mockMvc.perform(delete("/employees/deleteall")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("All employees deleted successfully"));
        verify(empService, times(1)).deleteAllEmployees();
    }

}
