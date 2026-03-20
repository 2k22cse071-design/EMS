package EMS.backend.controller;

import EMS.backend.dto.UserCreationRequest;
import EMS.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserCreationRequest request) {
        return ResponseEntity.ok(employeeService.createUnverifiedUser(request));
    }

    @GetMapping("/users/unverified")
    public ResponseEntity<?> getUnverified() {
        return ResponseEntity.ok(employeeService.getUnverifiedUsers());
    }
}
