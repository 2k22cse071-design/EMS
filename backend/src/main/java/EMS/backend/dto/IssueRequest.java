package EMS.backend.dto;

import lombok.Data;

@Data
public class IssueRequest {
    private String title;
    private String description;
    private Long assignedToUserId;
}
