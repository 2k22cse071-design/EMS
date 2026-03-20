package EMS.backend.service;

import EMS.backend.dto.WorkTaskDTO;
import EMS.backend.entity.WorkTask;
import java.util.List;

public interface TaskService {
    WorkTask assignTask(WorkTaskDTO dto, Long managerUserId);
    List<WorkTask> getEmployeeTasks(Long userId);
    List<WorkTask> getTasksByManager(Long managerUserId);
    WorkTask completeTask(Long taskId, Long userId);
    List<WorkTask> getAllTasks();
}
