package EMS.backend.controller;

import EMS.backend.dto.LeaveRequestDTO;
import EMS.backend.dto.MessageResponse;
import EMS.backend.entity.*;
import EMS.backend.repository.EmployeeRepository;
import EMS.backend.repository.LeaveRequestRepository;
import EMS.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/leaves")
public class LeaveController {
    @Autowired
    LeaveRequestRepository leaveRequestRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/apply")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> applyLeave(@RequestBody LeaveRequestDTO leaveRequestDTO, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Error: Employee record not found."));

        LeaveRequest leaveRequest = LeaveRequest.builder()
                .employee(employee)
                .startDate(leaveRequestDTO.getStartDate())
                .endDate(leaveRequestDTO.getEndDate())
                .reason(leaveRequestDTO.getReason())
                .status(LeaveStatus.PENDING_HR)
                .build();

        leaveRequestRepository.save(leaveRequest);
        return ResponseEntity.ok(new MessageResponse("Leave application submitted (Pending HR)."));
    }

    @GetMapping("/hr/pending")
    @PreAuthorize("hasAuthority('HR')")
    public ResponseEntity<?> getPendingHR() {
        return ResponseEntity.ok(leaveRequestRepository.findByStatus(LeaveStatus.PENDING_HR));
    }

    @PostMapping("/hr/review/{id}")
    @PreAuthorize("hasAuthority('HR')")
    public ResponseEntity<?> hrReview(@PathVariable Long id, @RequestParam boolean approve) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Leave request not found."));

        if (leaveRequest.getStatus() != LeaveStatus.PENDING_HR) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid status for HR review."));
        }

        if (approve) {
            leaveRequest.setStatus(LeaveStatus.PENDING_MANAGER);
            leaveRequestRepository.save(leaveRequest);
            return ResponseEntity.ok(new MessageResponse("Leave approved by HR (Pending Manager)."));
        } else {
            leaveRequest.setStatus(LeaveStatus.REJECTED);
            leaveRequestRepository.save(leaveRequest);
            return ResponseEntity.ok(new MessageResponse("Leave rejected by HR."));
        }
    }

    @GetMapping("/manager/pending")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<?> getPendingManager() {
        return ResponseEntity.ok(leaveRequestRepository.findByStatus(LeaveStatus.PENDING_MANAGER));
    }

    @PostMapping("/manager/review/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<?> managerReview(@PathVariable Long id, @RequestParam boolean approve) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Leave request not found."));

        if (leaveRequest.getStatus() != LeaveStatus.PENDING_MANAGER) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid status for Manager review."));
        }

        if (approve) {
            leaveRequest.setStatus(LeaveStatus.APPROVED);
            leaveRequestRepository.save(leaveRequest);
            return ResponseEntity.ok(new MessageResponse("Leave approved by Manager (Final)."));
        } else {
            leaveRequest.setStatus(LeaveStatus.REJECTED);
            leaveRequestRepository.save(leaveRequest);
            return ResponseEntity.ok(new MessageResponse("Leave rejected by Manager."));
        }
    }

    @GetMapping("/my-leaves")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> getMyLeaves(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        Employee employee = employeeRepository.findByUser(user).get();
        return ResponseEntity.ok(leaveRequestRepository.findByEmployee(employee));
    }
}
