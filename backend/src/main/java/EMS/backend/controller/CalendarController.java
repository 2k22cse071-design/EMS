package EMS.backend.controller;

import EMS.backend.service.TaskService;
import EMS.backend.service.LeaveService;
import EMS.backend.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private LeaveService leaveService;

    @GetMapping("/tasks")
    public ResponseEntity<?> getCalendarTasks(Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        boolean isEmployee = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"));
        boolean isManager = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"));
        boolean isHR = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_HR") || a.getAuthority().equals("ROLE_ADMIN"));

        Map<String, Object> response = new HashMap<>();

        try {
            if (isHR) {
                response.put("tasks", taskService.getAllTasks());
                response.put("leaves", leaveService.getAll());
            } else if (isManager) {
                response.put("tasks", taskService.getTasksByManager(userDetails.getId()));
                response.put("leaves", leaveService.getAllForManager(userDetails.getId()));
            } else if (isEmployee) {
                response.put("tasks", taskService.getEmployeeTasks(userDetails.getId()));
                response.put("leaves", leaveService.getEmployeeLeaves(userDetails.getId()));
            } else {
                // Default to empty or user-specific if fallback needed
                response.put("tasks", taskService.getEmployeeTasks(userDetails.getId()));
                response.put("leaves", leaveService.getEmployeeLeaves(userDetails.getId()));
            }
        } catch (Exception e) {
            // Log error and return empty lists instead of 400/500
            System.err.println("Error fetching calendar data for user " + userDetails.getId() + ": " + e.getMessage());
            response.put("tasks", java.util.Collections.emptyList());
            response.put("leaves", java.util.Collections.emptyList());
            response.put("error", "Some data could not be loaded");
        }

        
        return ResponseEntity.ok(response);
    }
}
