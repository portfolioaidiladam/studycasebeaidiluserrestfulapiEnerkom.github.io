package studycaseaplikasiapi.springbeaidil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import studycaseaplikasiapi.springbeaidil.entity.Employee;
import studycaseaplikasiapi.springbeaidil.model.EmployeeDTO;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAllByOrderByNameAsc(Pageable pageable);
}