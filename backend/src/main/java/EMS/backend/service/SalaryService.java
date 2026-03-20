package EMS.backend.service;

import EMS.backend.dto.SalaryRequest;
import EMS.backend.entity.Salary;
import java.util.List;

public interface SalaryService {
    Salary setSalary(SalaryRequest request);
    Salary getEmployeeSalary(Long userId);
    List<Salary> getAllSalaries(); // For HR
}
