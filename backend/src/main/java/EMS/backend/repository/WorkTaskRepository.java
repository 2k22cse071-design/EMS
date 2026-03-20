package EMS.backend.repository;

import EMS.backend.entity.WorkTask;
import EMS.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkTaskRepository extends JpaRepository<WorkTask, Long> {
    List<WorkTask> findByAssignedTo(Employee employee);
}
