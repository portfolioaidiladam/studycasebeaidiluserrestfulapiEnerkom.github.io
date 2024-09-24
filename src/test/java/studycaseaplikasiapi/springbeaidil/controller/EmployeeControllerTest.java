package studycaseaplikasiapi.springbeaidil.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import studycaseaplikasiapi.springbeaidil.entity.Employee;
import studycaseaplikasiapi.springbeaidil.model.EmployeeDTO;
import studycaseaplikasiapi.springbeaidil.service.EmployeeService;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser
    public void testCreateEmployee_Success() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("Aidil Adam");
        employeeDTO.setUserId(1L);

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Aidil Adam");

        when(employeeService.createEmployee(any(EmployeeDTO.class))).thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Aidil Adam"));
    }

    @Test
    @WithMockUser
    public void testCreateEmployee_Failure() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("Aidil Adam");
        employeeDTO.setUserId(1L);

        when(employeeService.createEmployee(any(EmployeeDTO.class))).thenThrow(new RuntimeException("Error creating employee"));

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Error creating employee"));
    }

    @Test
    @WithMockUser
    public void testUpdateEmployee_Success() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("Aidil Adam");

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Aidil Adam");

        when(employeeService.updateEmployee(eq(1L), any(EmployeeDTO.class))).thenReturn(employee);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Aidil Adam"));
    }

    @Test
    @WithMockUser
    public void testUpdateEmployee_Failure() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("Aidil Adam");

        when(employeeService.updateEmployee(eq(1L), any(EmployeeDTO.class))).thenThrow(new RuntimeException("Employee not found"));

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Employee not found"));
    }

    @Test
    @WithMockUser
    public void testDeleteEmployee_Success() throws Exception {
        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Employee deleted successfully"));
    }

    @Test
    @WithMockUser
    public void testDeleteEmployee_Failure() throws Exception {
        doThrow(new RuntimeException("Employee not found")).when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Employee not found"));
    }

    @Test
    @WithMockUser
    public void testListEmployees() throws Exception {
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("Aidil Adam");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("John Doe");

        Page<Employee> page = new PageImpl<>(Arrays.asList(employee1, employee2));

        when(employeeService.listEmployees(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Aidil Adam"))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[1].name").value("John Doe"));
    }
}