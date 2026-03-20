package EMS.backend.repository;

import EMS.backend.entity.Salary;
import EMS.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    Optional<Salary> findByEmployee(Employee employee);
}
