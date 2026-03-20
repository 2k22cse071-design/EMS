package EMS.backend.service;

import EMS.backend.dto.UserCreationRequest;
import EMS.backend.dto.VerificationApprovalRequest;
import EMS.backend.entity.Employee;
import EMS.backend.entity.User;
import java.util.List;

public interface EmployeeService {
    User createUnverifiedUser(UserCreationRequest request);
    Employee verifyEmployee(VerificationApprovalRequest request);
    List<User> getUnverifiedUsers();
    List<Employee> getAllEmployees();
    List<User> getManagers();
    Employee getEmployeeByUserId(Long userId);
}
