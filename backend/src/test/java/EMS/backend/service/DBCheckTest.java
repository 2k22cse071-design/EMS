package EMS.backend.service;

import EMS.backend.repository.EmployeeRepository;
import EMS.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DBCheckTest {
    @Autowired
    private EmployeeRepository empRepo;
    @Autowired
    private UserRepository userRepo;

    @Test
    public void checkDB() {
        System.out.println("--- USERS ---");
        userRepo.findAll().forEach(u -> System.out.println(u.getUsername() + " (" + u.getRole() + ") - Verified: " + u.isVerified()));
        System.out.println("--- EMPLOYEES ---");
        empRepo.findAll().forEach(e -> System.out.println("ID: " + e.getId() + " - User: " + e.getUser().getUsername()));
    }
}
