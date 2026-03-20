package EMS.backend.controller;

import EMS.backend.dto.LeaveRequestDTO;
import EMS.backend.service.LeaveService;
import EMS.backend.service.SalaryService;
import EMS.backend.service.TaskService;
import EMS.backend.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employee")
@PreAuthorize("hasRole('EMPLOYEE')")
public class EmployeeController {

    @Autowired
    private LeaveService leaveService;
 
    @Autowired
    private TaskService taskService;

    @Autowired
    private SalaryService salaryService;

    @PostMapping("/leave/request")
    public ResponseEntity<?> requestLeave(@RequestBody LeaveRequestDTO dto, Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return ResponseEntity.ok(leaveService.createLeaveRequest(dto, userDetails.getId()));
    }

    @GetMapping("/leaves")
    public ResponseEntity<?> getMyLeaves(Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return ResponseEntity.ok(leaveService.getEmployeeLeaves(userDetails.getId()));
    }
 
    @GetMapping("/tasks")
    public ResponseEntity<?> getMyTasks(Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return ResponseEntity.ok(taskService.getEmployeeTasks(userDetails.getId()));
    }
 
    @PostMapping("/task/complete/{id}")
    public ResponseEntity<?> completeTask(@PathVariable Long id, Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return ResponseEntity.ok(taskService.completeTask(id, userDetails.getId()));
    }

    @GetMapping("/salary")
    public ResponseEntity<?> getMySalary(Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return ResponseEntity.ok(salaryService.getEmployeeSalary(userDetails.getId()));
    }
}
