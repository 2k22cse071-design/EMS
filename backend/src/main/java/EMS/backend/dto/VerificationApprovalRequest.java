package EMS.backend.dto;

import lombok.Data;

@Data
public class VerificationApprovalRequest {
    private Long userId;
    private Long managerId;
    private Long departmentId;
    private String designation;
}
