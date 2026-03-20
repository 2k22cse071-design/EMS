package EMS.backend.service;

import EMS.backend.dto.IssueDTO;
import EMS.backend.entity.Issue;
import EMS.backend.entity.User;
import EMS.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IssueServiceManualTest {

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    @Test
    public void testCreateIssue() {
        try {
            try {
                jdbcTemplate.execute("ALTER TABLE issues DROP COLUMN IF EXISTS resolved");
                jdbcTemplate.execute("ALTER TABLE issues DROP COLUMN IF EXISTS target_role");
                jdbcTemplate.execute("ALTER TABLE issues ALTER COLUMN assigned_to_id DROP NOT NULL");
                jdbcTemplate.execute("ALTER TABLE issues DROP COLUMN IF EXISTS status"); // we have issue_status now
                System.out.println("DROPPED LEGACY FIELDS");
            } catch (Exception ex) {
                System.out.println("Could not alter: " + ex.getMessage());
            }

            // Find an existing user or create one
            User user = userRepository.findAll().stream().findFirst().orElseThrow();
            
            IssueDTO dto = new IssueDTO();
            dto.setTitle("Test Title");
            dto.setDescription("Test Description");
            dto.setTargetRole("HR");

            Issue issue = issueService.createIssue(dto, user.getId());
            System.out.println("CREATED ISSUE: " + issue.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
