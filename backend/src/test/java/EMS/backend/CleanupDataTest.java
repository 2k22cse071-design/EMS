package EMS.backend;

import EMS.backend.repository.EmployeeRepository;
import EMS.backend.repository.UserRepository;
import EMS.backend.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CleanupDataTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void cleanupEmployeeUsers() {
        System.out.println("Starting cleanup of EMPLOYEE role users...");
        
        // 1. Find all employees to delete their profiles first
        employeeRepository.deleteAll();
        System.out.println("Deleted all entries from the employees table.");

        // 2. Delete all users with role 'EMPLOYEE'
        userRepository.findAll().stream()
            .filter(user -> user.getRole() == Role.EMPLOYEE)
            .forEach(user -> userRepository.delete(user));
        
        System.out.println("Deleted all users with role 'EMPLOYEE'. Cleanup complete!");
    }
}
