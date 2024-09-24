package studycaseaplikasiapi.springbeaidil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studycaseaplikasiapi.springbeaidil.entity.Employee;
import studycaseaplikasiapi.springbeaidil.entity.User;
import studycaseaplikasiapi.springbeaidil.model.EmployeeDTO;
import studycaseaplikasiapi.springbeaidil.repository.EmployeeRepository;
import studycaseaplikasiapi.springbeaidil.repository.UserRepository;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    public Employee createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        User user = userRepository.findById(employeeDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        employee.setUser(user);
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setName(employeeDTO.getName());
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public Page<Employee> listEmployees(Pageable pageable) {
        return employeeRepository.findAllByOrderByNameAsc(pageable);
    }
}