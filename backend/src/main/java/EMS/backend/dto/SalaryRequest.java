package EMS.backend.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SalaryRequest {
    private Long employeeId;
    private BigDecimal amount;
    private String notes;
}
